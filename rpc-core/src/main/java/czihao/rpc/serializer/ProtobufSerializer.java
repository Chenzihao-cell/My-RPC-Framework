package czihao.rpc.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import czihao.rpc.enumeration.SerializerCode;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用ProtoBuf的序列化器
 */

/*
Protobuf对于不同的字段类型采用不同的编码方式和数据存储方式对消息字段进行序列化，以确保得到高效紧凑的数据压缩。

Protobuf编码方式
1.Varint编码
Varint编码是一种变长的编码方式，其编码原理是用字节表示数字，值越小的数字，使用越少的字节数表示。
因此，可以通过减少表示数字的字节数进行数据压缩。

2.Zigzag编码
Zigzag编码也是一种变长的编码方式，其编码原理是使用无符号数来表示有符号数字，
使得绝对值小的数字都可以采用较少字节来表示，特别对表示负数的数据能更好地进行数据压缩。

Protobuf数据存储方式
T-L-V数据存储方式
T-L-V（Tag - Length - Value），即标识符-长度-字段值的存储方式，
其原理是以标识符-长度-字段值表示单个数据，最终将所有数据拼接成一个字节流，从而实现数据存储的功能。

Protobuf序列化过程如下：
（1）判断每个字段是否有设置值，有值才进行编码。
（2）根据字段标识号与数据类型将字段值通过不同的编码方式进行编码。
（3）将编码后的数据块按照字段类型采用不同的数据存储方式封装成二进制数据流。

Protobuf反序列化过程如下：
（1）调用消息类的parseFrom(input)解析从输入流读入的二进制字节数据流。
（2）将解析出来的数据按照指定的格式读取到C++、Java、Phyton对应的结构类型中。

参考文章：https://www.jianshu.com/p/c83be8309554
* */
public class ProtobufSerializer implements CommonSerializer {

    private LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    private Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public byte[] serialize(Object obj) {
        Class clazz = obj.getClass();
        Schema schema = getSchema(clazz);
        byte[] data;
        try {
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            buffer.clear();
        }
        return data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Schema schema = getSchema(clazz);
        Object obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("PROTOBUF").getCode();
    }

    @SuppressWarnings("unchecked")
    private Schema getSchema(Class clazz) {
        Schema schema = schemaCache.get(clazz);
        if (Objects.isNull(schema)) {
            // 这个schema通过RuntimeSchema进行懒创建并缓存
            // 所以可以一直调用RuntimeSchema.getSchema(),这个方法是线程安全的
            schema = RuntimeSchema.getSchema(clazz);
            if (Objects.nonNull(schema)) {
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }

}
