package org.hptd.utils;

import com.google.common.base.Function;
import org.h2.jdbcx.JdbcConnectionPool;
import org.hptd.meta.NameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * the H2 database utilization class
 *
 * @author ford
 * @since 1.0
 */
public class H2DBUtil {
    private static JdbcConnectionPool cp = null;
    private static Logger logger = LoggerFactory.getLogger(H2DBUtil.class);
    private static final String MAPPER_TABLE = "CREATE TABLE IF NOT EXISTS nameMapper(innerId IDENTITY,hptdName VARCHAR(255) NOT NULL,metaClassId BIGINT)";
    private static final String MAPPER_NAME_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS unqiueMapperName ON nameMapper(hptdName)";
    private static final String META_TABLE = "CREATE TABLE IF NOT EXISTS metaClass(id IDENTITY,name VARCHAR(255) NOT NULL,label VARCHAR(255),columns VARCHAR(4096))";
    private static final String META_NAME_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS unqiueMetaName ON metaClass(name)";

    //private static final String INSERT_NAME_MAPPER = "INSERT INTO nameMapper(hptdName,metaClassId) VALUES(?,?)";
    private static final String INSERT_NAME_MAPPER = "MERGE INTO nameMapper(hptdName,metaClassId) KEY(hptdName) VALUES(?,?)";
    private static final String UPDATE_NAME_MAPPER = "UPDATE  nameMapper SET metaClassId = ? where  hptdName = ?";
    private static final String SELECT_NAME_MAPPER = "SELECT innerId,metaClassId FROM nameMapper where hptdName = ?";

    public static void init() {
        chkConnectionPool();
        Connection connection = null;
        try {
            connection = cp.getConnection();
            connection.prepareStatement(MAPPER_TABLE).execute();
            connection.prepareStatement(MAPPER_NAME_INDEX).execute();
            connection.prepareStatement(META_TABLE).execute();
            connection.prepareStatement(META_NAME_INDEX).execute();
        } catch (SQLException e) {
            logger.error("init h2db error.", e);
        } finally {
            closeConnection(connection);
        }
    }

    private static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("closeConnection error.", e);
            }
        }
    }

    private static void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error("closeStatement error.", e);
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("closeResultSet error.", e);
            }
        }
    }

    private static void chkConnectionPool() {
        if (cp == null) {
            cp = JdbcConnectionPool.create("jdbc:h2:" + HptdUtil.getRootPath() + "/metaData", "sa", "sa");
            cp.setMaxConnections(20);
        }
    }

    public static void destroy() {
        cp.dispose();
    }

    public static void saveOrUpdateNameMapper(final NameMapper nameMapper) {
        if (nameMapper.getInnerId() <= 0) {
            long innerId = executeUpdate(INSERT_NAME_MAPPER, new Function<PreparedStatement, Integer>() {
                @Override
                public Integer apply(PreparedStatement preparedStatement) {
                    try {
                        preparedStatement.setString(1, nameMapper.getHptdName());
                        preparedStatement.setLong(2, nameMapper.getMetaClassId());
                    } catch (SQLException e) {
                        logger.error("saveOrUpdateNameMapper error", e);
                    }
                    return Statement.RETURN_GENERATED_KEYS;
                }
            });
            nameMapper.setInnerId(innerId);
        } else {
            executeUpdate(UPDATE_NAME_MAPPER, new Function<PreparedStatement, Integer>() {
                @Override
                public Integer apply(PreparedStatement preparedStatement) {
                    try {
                        preparedStatement.setLong(1, nameMapper.getMetaClassId());
                        preparedStatement.setString(2, nameMapper.getHptdName());
                    } catch (SQLException e) {
                        logger.error("saveOrUpdateNameMapper error", e);
                    }
                    return Statement.NO_GENERATED_KEYS;
                }
            });
        }
    }

    public static NameMapper getNameMapper(final String hptdName) {
        return (NameMapper) executeQuery(SELECT_NAME_MAPPER, new Function<PreparedStatement, Object>() {
                    @Override
                    public Object apply(PreparedStatement preparedStatement) {
                        try {
                            preparedStatement.setString(1, hptdName);
                        } catch (SQLException e) {
                            logger.error("getNameMapper error,with name:" + hptdName, e);
                        }
                        return null;
                    }
                }, new Function<ResultSet, Object>() {
                    @Override
                    public Object apply(ResultSet resultSet) {
                        try {
                            if (resultSet.next()) {
                                return new NameMapper(hptdName, resultSet.getLong(2), resultSet.getLong(1));
                            }
                            return null;

                        } catch (SQLException e) {
                            logger.error("getNameMapper error,with name:" + hptdName, e);
                            return null;
                        }
                    }
                }
        );
    }

    private static Long executeUpdate(String sql, Function<PreparedStatement, Integer> function) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cp.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Integer genKey = function.apply(preparedStatement);
            preparedStatement.executeUpdate();
            if (genKey == Statement.RETURN_GENERATED_KEYS) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
            return 0l;
        } catch (SQLException e) {
            logger.error("executeUpdate error with: sql " + sql, e);
            return -1l;
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    private static Object executeQuery(String sql, Function<PreparedStatement, Object> function, Function<ResultSet, Object> resultSetObjectFunction) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = cp.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            function.apply(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            return resultSetObjectFunction.apply(resultSet);
        } catch (SQLException e) {
            logger.error("executeQuery error with: sql " + sql, e);
            return null;
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }
}
