package org.hptd.io;

import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;

/**
 * auto check the java leveldb or leveldbjni
 *
 * @author ford
 */
public class LeveldbFactoryAdapter {

    public static DB getDb(String name) throws IOException {
        Options options = new Options();
        options.createIfMissing(true).verifyChecksums(false).maxOpenFiles(512);
        options.compressionType(CompressionType.SNAPPY);
        File dbFile = new File(name);
        DB db = null;
        try {
            db = JniDBFactory.factory.open(dbFile, options);
        } catch (IOException e) {
            throw e;
        } catch (Throwable e) {
            db = Iq80DBFactory.factory.open(dbFile, options);
        }
        return db;
    }
}
