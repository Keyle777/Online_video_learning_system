package top.keyle.Online_video_learning_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.Online_video_learning_system.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(String userId);

    //根据用户分配角色
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    List<Role> selectRoleByUserId(String id);
}
