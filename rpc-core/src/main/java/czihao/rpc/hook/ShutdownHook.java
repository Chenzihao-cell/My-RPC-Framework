package czihao.rpc.hook;

import czihao.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author czihao
 */
public class ShutdownHook {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    private static final ShutdownHook shutdownHook = new ShutdownHook();

    /*
     * 仅在NettyServer.start()这一处被调用
     * */
    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    /*
     * 仅在NettyServer.start()这一处被调用
     * */
    public void addClearAllHook() {
        logger.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
        }));
    }

}
