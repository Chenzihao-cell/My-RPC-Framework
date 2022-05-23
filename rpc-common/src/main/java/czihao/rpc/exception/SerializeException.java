package czihao.rpc.exception;

/**
 * 序列化异常
 *
 * @author czihao
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}
