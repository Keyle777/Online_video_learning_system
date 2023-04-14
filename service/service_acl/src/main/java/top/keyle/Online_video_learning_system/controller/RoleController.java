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
import top.keyle.universal_tool.RespBeanEnum;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色分页列表
     * @param page 当前页码，必须大于0
     * @param limit 每页记录数，必须大于0
     * @param role 角色对象，可以包含角色名作为查询条件
     * @return RespBean对象，包含分页后的角色列表和总记录数
     */
    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public RespBean index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        // 创建一个分页对象，传入当前页码和每页记录数
        Page<Role> pageParam = new Page<>(page, limit);
        // 创建一个查询条件对象
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        // 如果角色对象中有角色名，就使用模糊查询
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        // 调用角色服务的分页方法，传入分页对象和查询条件对象
        roleService.page(pageParam,wrapper);
        // 创建一个哈希表，用于存放返回的数据
        HashMap<String, Object> hashMap = new HashMap<>();
        // 将分页后的角色列表放入哈希表中，键为"items"
        hashMap.put("items", pageParam.getRecords());
        // 将总记录数放入哈希表中，键为"total"
        hashMap.put("total", pageParam.getTotal());
        // 返回一个成功的RespBean对象，传入哈希表作为数据
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
        boolean b = roleService.removeById(id);
        if(b){
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return RespBean.success();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public RespBean batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return RespBean.success();
    }

    /**
     * 获取所有角色的名称列表
     * @return RespBean对象，包含所有角色的名称列表
     */
    @GetMapping("getRoleNameList")
    public RespBean getRoleNameList(){
        // 调用角色服务的列表方法，获取所有角色对象
        List<Role> roleList = roleService.list();
        // 返回一个成功的RespBean对象，传入所有角色的名称列表作为数据，键为"roleList"
        return RespBean.success("roleList",roleList);
    }
}

