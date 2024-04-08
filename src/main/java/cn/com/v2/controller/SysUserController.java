package cn.com.v2.controller;


import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.SysUser;
import cn.com.v2.model.Type;
import cn.com.v2.service.ISysUserService;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Objects;

import static cn.com.v2.common.domain.AjaxResult.error;
import static cn.com.v2.common.domain.AjaxResult.success;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/api/sys-user")
public class SysUserController {
    @Autowired
    private ISysUserService sysUserService;

    // list接口
    @GetMapping("/list")
    public AjaxResult list(int page, int size, String name, String nickname) {
        Page<SysUser> p = new Page<>(page, size);
        // 不为空 条件查询
        if (!Objects.equals(name, "") || !Objects.equals(nickname, "")) {
            // 条件查询
            Page<SysUser> list = sysUserService.getUserByNameOrNickname(p, name, nickname);
            JSONObject data = new JSONObject();
            data.putOnce("total", list.getTotal());
            data.putOnce("employees", list.getRecords());
            data.putOnce("current", list.getCurrent());
            data.putOnce("size", list.getSize());
            return success().put("data", data);
        }

        // 直接分页查询
        Page<SysUser> pageList = sysUserService.page(p);
        JSONObject data = new JSONObject();
        data.putOnce("total", pageList.getTotal());
        data.putOnce("employees", pageList.getRecords());
        data.putOnce("current", pageList.getCurrent());
        data.putOnce("size", pageList.getSize());
        return success().put("data", data);
    }

    // 添加或者更新用户
    @PostMapping("/update")
    public AjaxResult update(@RequestBody SysUser sysUser) {
        // id不为空是更新
        if (sysUser.getId() != null && !sysUser.getId().equals("")) {
            boolean update = sysUserService.updateById(sysUser);
            if (update) {
                return success();
            }
            return error();
        }
        // id为空是插入
        boolean save = sysUserService.save(sysUser);
        if (save) {
            return success();
        }
        return error();
    }

    // 删除用户
    @DeleteMapping("/remove")
    public AjaxResult remove(String id) {
        boolean remove = sysUserService.removeById(id);
        if (remove) {
            return success();
        }
        return error();
    }

    // 批量删除用户
    @DeleteMapping("/remove-batch")
    public AjaxResult removeBatch(String[] ids) {
        boolean remove = sysUserService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return success();
        }
        return error();
    }

}
