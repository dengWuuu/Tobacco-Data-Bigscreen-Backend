package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.TobaccoShop;
import cn.com.v2.service.TobaccoShopService;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static cn.com.v2.common.domain.AjaxResult.error;
import static cn.com.v2.common.domain.AjaxResult.success;

/**
 * @author Wu
 * @date 2024年03月31日 21:19
 */
@RestController
@Slf4j
@Api(value = "商铺位置接口")
@RequestMapping("/api/shop")
public class TobaccoShopController {
    @Autowired
    private V2Config v2Config;
    @Autowired
    private TobaccoShopService tobaccoShopService;

    // 获取所有店铺
    @GetMapping("/list")
    public AjaxResult list(int page, int size, String shopBase, String shopDetail) {
        // 判断是不是条件查询
        if (!Objects.equals(shopBase, "") || !Objects.equals(shopDetail, "")) {
            Page<TobaccoShop> list = tobaccoShopService.selectByBaseOrDetail(page, size, shopBase, shopDetail);
            JSONObject data = new JSONObject();
            data.putOnce("shops", list.getRecords());
            data.putOnce("total", list.getTotal());
            data.putOnce("size", list.getSize());
            data.putOnce("current", list.getCurrent());
            return success().put("data", data);
        }


        // 分页查询 构造page
        Page<TobaccoShop> p = new Page<>(page, size);
        Page<TobaccoShop> pageList = tobaccoShopService.page(p);
        List<TobaccoShop> list = pageList.getRecords();
        JSONObject data = new JSONObject();
        data.putOnce("shops", list);

        // 放入页数相关信息
        data.putOnce("total", pageList.getTotal());
        data.putOnce("size", pageList.getSize());
        data.putOnce("current", pageList.getCurrent());

        if (list != null) {
            return success().put("data", data);
        }
        return error();
    }

    // 添加商铺
    @PostMapping("/update")
    public AjaxResult add(@RequestBody TobaccoShop tobaccoShop) {
        boolean save;
        if (Objects.equals(tobaccoShop.getId(), "")) save = tobaccoShopService.save(tobaccoShop);
        else save = tobaccoShopService.updateById(tobaccoShop);

        if (save) {
            return success();
        }
        return error();
    }

    // 删除商铺
    @DeleteMapping("/remove")
    public AjaxResult remove(String id) {
        boolean remove = tobaccoShopService.removeById(id);
        if (remove) {
            return success();
        }
        return error();
    }

    // 批量删除商铺
    @DeleteMapping("/remove-batch")
    public AjaxResult removeBatch(String[] ids) {
        boolean remove = tobaccoShopService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return success();
        }
        return error();
    }

    @GetMapping("/get-area-value")
    public AjaxResult getAreaValue() {
        Map<String, Integer> map = tobaccoShopService.getBaseCnt();
        JSONObject source = new JSONObject() {{
            putOnce("dimensions", new String[]{"name", "value"});
            List<JSONObject> list = new LinkedList<>();

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOnce("name", entry.getKey());
                jsonObject.putOnce("value", entry.getValue());
                list.add(jsonObject);
            }
            putOnce("source", list);

        }};
        return success().put("data", source);
    }

}
