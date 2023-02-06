package top.keyle.online_video_learning_system.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @Author OY
 * @Date 2021/3/20
 */
public class AliyunVodSDKUtil {
    public static IAcsClient initVodClient(String accessKeyId, String accessKeySecret){
        // 点播服务接入区域
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
