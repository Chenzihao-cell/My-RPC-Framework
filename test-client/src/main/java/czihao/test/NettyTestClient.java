package czihao.test;

import czihao.rpc.api.ByeService;
import czihao.rpc.api.HelloObject;
import czihao.rpc.api.HelloService;
import czihao.rpc.loadbalancer.LoadBalancer;
import czihao.rpc.loadbalancer.RandomLoadBalancer;
import czihao.rpc.serializer.CommonSerializer;
import czihao.rpc.transport.RpcClient;
import czihao.rpc.transport.RpcClientProxy;
import czihao.rpc.transport.netty.client.NettyClient;

/**
 * 测试用Netty消费者
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.HESSIAN_SERIALIZER, new RandomLoadBalancer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }

}
