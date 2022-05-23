package czihao.rpc.transport;

import czihao.rpc.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 *
 * @author czihao
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);

}
