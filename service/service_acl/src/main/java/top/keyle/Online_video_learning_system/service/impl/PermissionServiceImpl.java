package top.keyle.Online_video_learning_system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyle.Online_video_learning_system.entity.Permission;
import top.keyle.Online_video_learning_system.entity.RolePermission;
import top.keyle.Online_video_learning_system.entity.User;
import top.keyle.Online_video_learning_system.helper.MemuHelper;
import top.keyle.Online_video_learning_system.helper.PermissionHelper;
import top.keyle.Online_video_learning_system.mapper.PermissionMapper;
import top.keyle.Online_video_learning_system.service.PermissionService;
import top.keyle.Online_video_learning_system.service.RolePermissionService;
import top.keyle.Online_video_learning_system.service.RoleService;
import top.keyle.Online_video_learning_system.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    RoleService roleService;

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

    // 把返回所有菜单list集合进行封装的方法
    public static List<Permission> bulidPermission(List<Permission> permissionsList) {

        // 创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();
        // 把所有菜单list集合遍历，得到顶层菜单 pid = 0 菜单，设置level是1
        for (Permission permissionNode : permissionsList) {
            // 得到顶层菜单 pid = 0 菜单
           if("0".equals(permissionNode.getPid())){
               permissionNode.setLevel(1);
               // 根据顶层菜单，向里面进行查询子菜单，封装finalNode里面
               finalNode.add(selectChildren(permissionNode,permissionsList));
           }
        }
        return finalNode;
    }

    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionsList) {
        // 1 因为向一层菜单里面放二级菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());

        // 2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (Permission it : permissionsList) {
            // 判断 id 和 pid 值是否相同
            if(permissionNode.getId().equals(it.getPid())){
                // 把父菜单的level值+1
                int level  = permissionNode.getLevel()+1;
                it.setLevel(level);
                // 如果children为空，进行初始化操作
                if(permissionNode.getChildren() == null){
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                permissionNode.getChildren().add(selectChildren(it,permissionsList));
            }
        }

        return permissionNode;
    }

    /**
     * 查询所有菜单并根据角色 ID 设置选择状态
     *
     * @param roleId 角色 ID
     * @return 菜单列表
     */
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        // 查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        // 根据角色 ID 获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        // 遍历所有菜单，如果角色拥有该菜单的权限，则设置选择状态为 true
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }



    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        User user = userService.getById(userId);
        String roleId = user.getRoleId();
        String roleName = roleService.getById(roleId).getRoleName();
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
            List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
            List<JSONObject> result = MemuHelper.bulid(permissionList);
            return result;
        } else {
            selectPermissionList = baseMapper.selectList(null);
            List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
            List<JSONObject> result = MemuHelper.bulid(permissionList);
            //6是订单列表 5是幻灯片 4是统计分析 3是课程管理 2是课程分类管理 1是讲师管理 0是权限管理
            List<JSONObject> jsonObjects = new ArrayList<>();
            // 课程管理员拥有 3 2
            if("课程管理员".equals(roleName)){
                jsonObjects.add(result.get(4));
                jsonObjects.add(result.get(3));
                jsonObjects.add(result.get(2));
                jsonObjects.add(result.get(1));
            }
            // 讲师管理员拥有 1
            if("讲师管理员".equals(roleName)){
                jsonObjects.add(result.get(1));
            }
            // 普通管理员拥有 4 5 6
            if("普通管理员".equals(roleName)){
                jsonObjects.add(result.get(5));
                jsonObjects.add(result.get(6));
            }
            return jsonObjects;
        }
    }

    //========================递归查询所有菜单================================================

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "1".equals(user.getRoleId())) {
            return true;
        }
        return false;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<Permission> queryAllMenu() {
        // 1. 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionsList = baseMapper.selectList(wrapper);
        // 2. 把查询所有菜单list集合按照要求进行封装
        List<Permission> resultList = bulidPermission(permissionsList);
        return resultList;
    }

    /**
     *  递归删除菜单
     * @param id
     */
    @Override
    public void removoChildById(String id) {
        // 1. 创建list集合，用于封装所有删除菜单Id值
        List<String> idList = new ArrayList<>();
        // 2. 向idList集合设置删除菜单id
        this.selectPermissionChilderByid(id,idList);
        // 把点前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
     * @param id
     * @param idList
     */
    private void selectPermissionChilderByid(String id, List<String> idList) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        // 把childIdlist里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            // 封装idList里面
            idList.add(item.getId());
            // 递归查询
            this.selectPermissionChilderByid(item.getId(),idList);
        });
    }
    /**
     * 保存角色和权限之间的关系
     * @param roleId 角色id
     * @param permissionIds 菜单id数组
     */
    @Override
    public void saveRolePermissionRealtionShips(String roleId, String[] permissionIds) {
        // roleId 角色id
        // permissionId菜单id 数组形式
        // 1 创建list集合，用于封装添加数据
        ArrayList<RolePermission> rolePermissionList = new ArrayList<>();
        // 2 遍历所有菜单数组
        for(String perId : permissionIds){
            // 3 创建RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            // 4 封装到list集合
            rolePermissionList.add(rolePermission);
        }
        // 5 添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
