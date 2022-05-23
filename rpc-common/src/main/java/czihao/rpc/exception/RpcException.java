package czihao.rpc.exception;

import czihao.rpc.enumeration.RpcError;

/**
 * RPC调用异常
 *
 * @author czihao
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}
