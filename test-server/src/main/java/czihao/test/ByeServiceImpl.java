package czihao.test;

import czihao.rpc.annotation.Service;
import czihao.rpc.api.ByeService;

@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
