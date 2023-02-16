package top.keyle.Online_video_learning_system.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entity.Role;
import top.keyle.Online_video_learning_system.service.RoleService;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public RespBean index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("items", pageParam.getRecords());
        hashMap.put("total", pageParam.getTotal());
        return RespBean.success(hashMap);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public RespBean get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return RespBean.success("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public RespBean save(@RequestBody Role role) {
        roleService.save(role);
        return RespBean.success();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public RespBean updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return RespBean.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public RespBean remove(@PathVariable String id) {
        roleService.removeById(id);
        return RespBean.success();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public RespBean batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return RespBean.success();
    }

    @GetMapping("getRoleNameList")
    public RespBean getRoleNameList(){
        List<Role> roleList = roleService.list();
        return RespBean.success("roleList",roleList);
    }
}

