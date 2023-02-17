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


    //根据用户获取角色数据
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

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色
    @Override
    public void saveUserRoleRealtionShip(String userId, String[] roleIds) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        userService.remove(new QueryWrapper<User>().eq("id", userId));
        List<User> userRoleList = new ArrayList<>();
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

    @Override
    public List<Role> selectRoleByUserId(String id) {
        //根据用户id拥有的角色id
        List<User> userRoleList = userService.list(new QueryWrapper<User>().eq("id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
