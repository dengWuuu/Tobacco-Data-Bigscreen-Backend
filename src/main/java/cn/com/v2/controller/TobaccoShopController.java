package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.TobaccoShop;
import cn.com.v2.service.TobaccoShopService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public AjaxResult list() {
        List<TobaccoShop> list = tobaccoShopService.list();
        if (list != null) {
            return success().put("data", list);
        }
        return error();
    }

    @GetMapping("/get_area_value")
    public AjaxResult getAreaValue() {
        List<TobaccoShop> list = tobaccoShopService.list();
        if (list == null) {
            return error();
        }
        // 用 map 统计每个地区多少个店
        Map<String, Integer> map = new HashMap<>();
        for (TobaccoShop tobaccoShop : list) {
            map.put(tobaccoShop.getBase(), map.getOrDefault(tobaccoShop.getBase(), 0) + 1);
        }
        return success().put("data", map);
    }

}
