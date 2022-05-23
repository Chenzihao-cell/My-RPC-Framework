package czihao.rpc.transport;

import czihao.rpc.entity.RpcRequest;
import czihao.rpc.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 *
 * @author czihao
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}
