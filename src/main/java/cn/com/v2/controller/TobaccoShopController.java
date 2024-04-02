package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.TobaccoShop;
import cn.com.v2.service.TobaccoShopService;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
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
