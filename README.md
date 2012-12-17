hptd-framework
--------------

HPTD(High Performance Timestamp-based Data) is a framework only process the time-based column datas.

The [leveldb]: https://code.google.com/p/leveldb/ is a good framework to process the big data with great performance,so the HPTD's foundation is leveldb.

Here, we deal with the high level interface such as REST/socket APIs,meta data management,basic web-ui management.


Chunk Data format
=================

Name  | Byte Length | Description |
----- | ----------- | ------------|
typeSize|2|the type list size|
typeList|var length| the columns type list,a type is a 4 bits value|
dataList|var length| the data list|

Data Type Table
=================

Name | Value | Abbr Char| Byte Length |
---- | ----- | -------- | ----------- |
null|0|n|0|
byte|1|b|1|
short|2|s|2|
int|3|i|4|
long|4|l|8|
float|5|f|4|
double|d|n|8|
char|7|c|2|
string|8|t|var|
byte array|9|y|var|



