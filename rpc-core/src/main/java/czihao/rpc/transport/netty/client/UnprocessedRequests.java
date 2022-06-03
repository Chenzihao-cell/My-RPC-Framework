package czihao.rpc.transport.netty.client;

import czihao.rpc.entity.RpcResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UnprocessedRequests是单例类
 * UnprocessedRequests作为NettyClient类和NettyClientHandler类的属性成员
 *
 * @author czihao
 */
public class UnprocessedRequests {

    private static ConcurrentHashMap<String, CompletableFuture<RpcResponse>> unprocessedResponseFutures = new ConcurrentHashMap<>();


    /*
     * 仅在NettyClient.sendRequest(RpcRequest rpcRequest)这一处被调用过
     * */
    public void put(String requestId, CompletableFuture<RpcResponse> future) {
        unprocessedResponseFutures.put(requestId, future);
    }

    /*
     * 仅在NettyClient.sendRequest(RpcRequest rpcRequest)中
     * “catch (InterruptedException e)”这一处被调用过.
     * */
    public void remove(String requestId) {
        unprocessedResponseFutures.remove(requestId);
    }

    /*
     * 仅在NettyClientHandler.channelRead0(ChannelHandlerContext ctx, RpcResponse msg)这
     * 一处被调用过.
     * */
    public void complete(RpcResponse rpcResponse) {
        CompletableFuture<RpcResponse> future = unprocessedResponseFutures.remove(rpcResponse.getRequestId());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }

}
