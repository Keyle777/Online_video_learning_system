package top.keyle.Online_video_learning_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.keyle.Online_video_learning_system.entity.User;
import top.keyle.Online_video_learning_system.service.RoleService;
import top.keyle.Online_video_learning_system.service.UserService;
import top.keyle.universal_tool.MD5;
import top.keyle.universal_tool.RespBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/acl/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public RespBean index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
             User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("items", pageModel.getRecords());
        hashMap.put("total", pageModel.getTotal());
        return RespBean.success(hashMap);
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public RespBean save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return RespBean.success();
    }
    @ApiOperation(value = "根据id查询用户")
    @GetMapping("get/{id}")
    public RespBean updateById(@PathVariable("id") String id) {
        User useid = userService.getById(id);
        return RespBean.success("item",useid);
    }


    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public RespBean updateById(@RequestBody User user) {
        userService.updateById(user);
        return RespBean.success();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public RespBean remove(@PathVariable String id) {
        userService.removeById(id);
        return RespBean.success();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public RespBean batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return RespBean.success();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public RespBean toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return RespBean.success(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public RespBean doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return RespBean.success();
    }
}

