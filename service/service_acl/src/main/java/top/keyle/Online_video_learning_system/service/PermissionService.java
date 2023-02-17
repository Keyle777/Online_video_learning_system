package top.keyle.Online_video_learning_system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import top.keyle.Online_video_learning_system.entity.Permission;

import java.util.List;


public interface PermissionService extends IService<Permission> {

    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    // 获取全部菜单
    List<Permission> queryAllMenu();

    // 递归删除菜单
    void removoChildById(String id);

    // 给角色分配权限
    void saveRolePermissionRealtionShips(String roleId, String[] permissionId);
}
