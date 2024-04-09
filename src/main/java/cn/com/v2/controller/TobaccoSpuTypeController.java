package cn.com.v2.controller;

import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.Type;
import cn.com.v2.service.TobaccoSpuTypeService;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cn.com.v2.common.domain.AjaxResult.error;
import static cn.com.v2.common.domain.AjaxResult.success;

/**
 * @author Wu
 * @date 2024年04月06日 16:25
 */
@RestController
@Slf4j
@Api(value = "商品接口")
@RequestMapping("/api/spu_type")
public class TobaccoSpuTypeController {
    @Autowired
    private TobaccoSpuTypeService tobaccoSpuTypeService;

    // list接口 返回所有商品类型
    @GetMapping("/list")
    public AjaxResult list(int page, int size, String name) {
        if (page == 0 && size == 0) {
            // 不分页查询
            JSONObject data = new JSONObject();
            List<Type> list = tobaccoSpuTypeService.list();
            data.putOnce("spuTypes", list);
            data.putOnce("total", list .size());
            return success().put("data", data);
        }
        Page<Type> p = new Page<>(page, size);
        // 不为空 条件查询
        if (name == null || !Objects.equals(name, "")) {
            // 条件查询
            Page<Type> list = tobaccoSpuTypeService.selectByName(p, name);
            JSONObject data = new JSONObject();
            data.putOnce("total", list.getTotal());
            data.putOnce("spuTypes", list.getRecords());
            data.putOnce("current", list.getCurrent());
            data.putOnce("size", list.getSize());
            return success().put("data", data);
        }

        // 直接分页查询
        Page<Type> pageList = tobaccoSpuTypeService.page(p);
        JSONObject data = new JSONObject();
        data.putOnce("total", pageList.getTotal());
        data.putOnce("spuTypes", pageList.getRecords());
        data.putOnce("current", pageList.getCurrent());
        data.putOnce("size", pageList.getSize());
        return success().put("data", data);
    }

    // update接口
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Type type) {
        // id不为空是更新
        if (type.getId() != null && !type.getId().equals("")) {
            boolean update = tobaccoSpuTypeService.updateById(type);
            if (update) {
                return success();
            }
            return error();
        }
        // id为空是插入
        boolean save = tobaccoSpuTypeService.save(type);
        if (save) {
            return success();
        }
        return error();
    }

    // 删除接口
    @DeleteMapping("/remove")
    public AjaxResult remove(String id) {
        boolean remove = tobaccoSpuTypeService.removeById(id);
        if (remove) {
            return success();
        }
        return error();
    }

    // 批量删除接口
    @DeleteMapping("/remove-batch")
    public AjaxResult removeBatch(String[] ids) {
        boolean remove = tobaccoSpuTypeService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return success();
        }
        return error();
    }
}
