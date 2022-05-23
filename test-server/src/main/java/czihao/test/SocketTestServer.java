package czihao.test;

import czihao.rpc.annotation.ServiceScan;
import czihao.rpc.serializer.CommonSerializer;
import czihao.rpc.transport.RpcServer;
import czihao.rpc.transport.socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 *
 * @author czihao
 */
@ServiceScan
public class SocketTestServer {

    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }

}
