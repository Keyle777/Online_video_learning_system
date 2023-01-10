package top.keyle.msm_service.service;
import java.util.Map;

/**
 * @author TMJIE5200
 * @date 2023-01-10 14:43:57
 * @week 星期二
 */
public interface MsmService {
    boolean send(String PhoneNumbers, String templateCode,
                        Map<String, Object> param);
}
