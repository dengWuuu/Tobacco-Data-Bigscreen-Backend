package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.SpuType;
import cn.com.v2.model.vo.TobaccoSellRecordVo;
import cn.com.v2.service.TobaccoSellRecordService;
import cn.hutool.json.JSONObject;
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
 * @date 2024年04月01日 16:41
 */
@Api(value = "用户购买记录接口")
@RestController
@RequestMapping("/api/sell-record")
@Slf4j
public class TobaccoSellRecordController {
    @Autowired
    private V2Config v2Config;
    @Autowired
    private TobaccoSellRecordService tobaccoSellRecordService;

    // 获取所有购买记录
    @GetMapping("/list")
    public AjaxResult list() {
        // 获取所有购买记录
        List<TobaccoSellRecordVo> list = tobaccoSellRecordService.getAllSellRecordInfo();
        if (list != null) {
            return success().put("data", list);
        }
        return error();
    }

    // 返回销售的 spu 类型 json
    @GetMapping("/type-count")
    public AjaxResult typeCount() {
        Map<Integer, Integer> sellTypeCount = tobaccoSellRecordService.getSellTypeCount();
        int tobaccoCount = 0;
        if (sellTypeCount.containsKey(SpuType.TOBACCO.getCode())) {
            tobaccoCount = sellTypeCount.get(SpuType.TOBACCO.getCode());
        }

        int other = 0;
        for (Map.Entry<Integer, Integer> entry : sellTypeCount.entrySet()) {
            if (entry.getKey() == SpuType.TOBACCO.getCode()) continue;
            other += entry.getValue();
        }
        // 烟草相关信息
        int finalTobaccoCount = tobaccoCount;
        HashMap<String, Object> tobaccoHashMap = new HashMap<String, Object>() {{
            put("product", "卷烟销售");
            put("data", finalTobaccoCount);
        }};
        // 其他商品相关信息
        int finalOther = other;
        HashMap<String, Object> otherHashMap = new HashMap<String, Object>() {{
            put("product", "非卷烟销售");
            put("data", finalOther);
        }};

        // 构造返回给数据大屏的 json 数据

        JSONObject source = new JSONObject() {{
            putOnce("dimensions", new String[]{"product", "data"});
            putOnce("source", new HashMap[]{tobaccoHashMap, otherHashMap});
        }};
        return success().put("data", source);
    }

    // 返回销售的 spu 类型毛利率 json
    @GetMapping("/type-profit")
    public AjaxResult typeProfit() {
        Map<Integer, Double> sellTypeProfit = tobaccoSellRecordService.getSellTypeProfit();
        HashMap<Object, Object> tobaccoHashMap = new HashMap<Object, Object>() {{
            put("product", "卷烟毛利率");
            put("data", sellTypeProfit.get(SpuType.TOBACCO.getCode()));
        }};
        HashMap<Object, Object> otherHashMap = new HashMap<Object, Object>() {{
            put("product", "非卷烟毛利率");
            put("data", sellTypeProfit.get(SpuType.OTHER.getCode()));
        }};

        JSONObject source = new JSONObject() {{
            putOnce("dimensions", new String[]{"product", "data"});
            putOnce("source", new HashMap[]{tobaccoHashMap, otherHashMap});
        }};
        return success().put("data", source);
    }
}
