package top.keyle.Online_video_learning_system.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entity.Permission;
import top.keyle.Online_video_learning_system.service.PermissionService;
import top.keyle.universal_tool.RespBean;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public RespBean indexAllPermission(){
        List<Permission> list = permissionService.queryAllMenu();
        return RespBean.success("children",list);
    }

    @ApiOperation(value="递归删除菜单")
    @DeleteMapping("remove/{id}")
    public RespBean remove(@PathVariable String id){
        permissionService.removoChildById(id);
        return RespBean.success();
    }

    @ApiOperation(value="给角色分配权限")
    @PostMapping("/doAssign")
    public RespBean doAssign(String roleId, String[] permissionId){
        permissionService.saveRolePermissionRealtionShips(roleId,permissionId);
        return RespBean.success();
    }



    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public RespBean toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return RespBean.success("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public RespBean save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return RespBean.success();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public RespBean updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return RespBean.success();
    }

}

