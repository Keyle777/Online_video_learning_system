package top.keyle.Online_video_learning_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.entity.Role;
import top.keyle.Online_video_learning_system.entity.User;
import top.keyle.Online_video_learning_system.mapper.RoleMapper;
import top.keyle.Online_video_learning_system.service.RoleService;
import top.keyle.Online_video_learning_system.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserService userService;


    /**
     * 根据用户 ID 查找角色
     *
     * @param userId 用户 ID
     * @return 包含已分配角色和所有角色的 Map 对象
     */
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        //查询所有的角色
        List<Role> allRolesList =baseMapper.selectList(null);
        //根据用户id，查询用户拥有的角色id
        List<User> existUserRoleList = userService.list(new QueryWrapper<User>().eq("id", userId).select("role_id"));
        List<String> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());
        //对角色进行分类
        List<Role> assignRoles = new ArrayList<Role>();
        for (Role role : allRolesList) {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }
        // 创建 Map 对象并返回结果
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    /**
     * 保存用户角色关系
     *
     * @param userId 用户 ID
     * @param roleIds 角色 ID 数组
     */
    @Override
    public void saveUserRoleRealtionShip(String userId, String[] roleIds) {
        // 根据用户 ID 查询用户信息
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        // 删除原有的用户角色关系
        userService.remove(new QueryWrapper<User>().eq("id", userId));
        List<User> userRoleList = new ArrayList<>();
        // 遍历角色 ID 数组，创建新的用户角色关系并保存
        for(String roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) {
                continue;
            }
            User userRole = new User();
            userRole.setId(userId);
            userRole.setRoleId(roleId);
            userRole.setUsername(user1.getUsername());
            userRole.setNickName(user1.getNickName());
            userRole.setPassword(user1.getPassword());
            userRole.setSalt(user1.getSalt());
            userRole.setToken(user1.getToken());
            userRoleList.add(userRole);
        }
        userService.saveBatch(userRoleList);
    }

    /**
     * 根据用户 ID 查询拥有的角色ID
     *
     * @param id 用户 ID
     * @return 角色ID列表
     */
    @Override
    public List<Role> selectRoleByUserId(String id) {
        // 根据用户 ID 查询用户拥有的角色 ID
        List<User> userRoleList = userService.list(new QueryWrapper<User>().eq("id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        // 如果角色 ID 列表不为空，则查询角色信息并返回结果
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
