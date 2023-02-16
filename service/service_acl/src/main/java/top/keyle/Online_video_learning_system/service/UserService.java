package top.keyle.Online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.Online_video_learning_system.entity.User;

public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
