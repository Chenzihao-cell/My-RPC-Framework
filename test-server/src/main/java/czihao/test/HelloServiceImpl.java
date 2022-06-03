package czihao.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import czihao.rpc.annotation.Service;
import czihao.rpc.api.HelloObject;
import czihao.rpc.api.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "这是Impl1方法";
    }

}
