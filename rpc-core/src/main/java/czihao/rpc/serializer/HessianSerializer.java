package czihao.rpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import czihao.rpc.enumeration.SerializerCode;
import czihao.rpc.exception.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 基于Hessian协议的序列化器
 */
/*
 * Hessian原理分析
 * 总结：得益于 hessian 序列化和反序列化的实现机制， hessian 序列化的速度很快，而且序列化后的字节数也较其他技术少。
 * 另外：在远程调用中，需要把参数和返回值通过网络传输，这个使用就要用到序列化将对象转变成字节流，
 * 从一端到另一端之后再反序列化回来变成对象。这里就简单讲讲Java序列化和hessian序列化的区别。
 * 首先，hessian序列化比Java序列化高效很多，而且生成的字节流也要短很多。
 * 但相对来说没有Java序列化可靠，而且也不如Java序列化支持的全面。而之所以会出现这样的区别，则要从它们的实现方式来看。
 * 先说Java序列化，Java序列化会把要序列化的对象类的元数据和业务数据全部序列化成字节流，
 * 而且是把整个继承关系上的东西全部序列化了。
 * 它序列化出来的字节流是对那个对象结构到内容的完全描述，包含所有的信息，因此效率较低而且字节流比较大。
 * 但是由于确实是序列化了所有内容，所以可以说什么都可以传输，因此也更可用和可靠。
 *
 * 而hessian序列化，它的实现机制是着重于数据，附带简单的类型信息的方法。
 * 就像Integer a = 1，hessian会序列化成I 1这样的流，I表示int or Integer，1就是数据内容。
 * 而对于复杂对象，通过Java的反射机制，hessian把对象所有的属性当成一个Map来序列化，
 * 产生类似M className propertyName1 I 1 propertyName S stringValue（大概如此，确切的忘了）这样的流，
 * 包含了基本的类型描述和数据内容。
 * 而在序列化过程中，如果一个对象之前出现过，hessian会直接插入一个R index这样的块来表示一个引用位置，
 * 从而省去再次序列化和反序列化的时间。
 * hessian因为没有深入到实现内部去进行序列化，所以在某些场合会发生一定的不一致。
 * 参考文献：https://www.cnblogs.com/xingzc/p/5754017.html
 * */
public class HessianSerializer implements CommonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(HessianSerializer.class);

    @Override
    public byte[] serialize(Object obj) {
        HessianOutput hessianOutput = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        } finally {
            if (hessianOutput != null) {
                try {
                    hessianOutput.close();
                } catch (IOException e) {
                    logger.error("关闭流时有错误发生:", e);
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        HessianInput hessianInput = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            hessianInput = new HessianInput(byteArrayInputStream);
            return hessianInput.readObject();
        } catch (IOException e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        } finally {
            if (hessianInput != null) hessianInput.close();
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("HESSIAN").getCode();
    }
}
