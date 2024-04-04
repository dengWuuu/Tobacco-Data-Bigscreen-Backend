package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.SpuType;
import cn.com.v2.model.TobaccoSpu;
import cn.com.v2.service.TobaccoSpuService;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static cn.com.v2.common.domain.AjaxResult.error;
import static cn.com.v2.common.domain.AjaxResult.success;

/**
 * @author Wu
 * @date 2024年04月01日 17:41
 */
@RestController
@Slf4j
@Api(value = "商品接口")
@RequestMapping("/api/spu")
public class TobaccoSpuController {
    @Autowired
    private V2Config v2Config;
    @Autowired
    private TobaccoSpuService tobaccoSpuService;

    // 分页查询商品
    @GetMapping("/list")
    public AjaxResult list(int current, int size) {
        List<TobaccoSpu> list = tobaccoSpuService.list();
        if (list != null) {
            return success().put("data", list);
        }
        return error();
    }

    // 分页查询商品
    @GetMapping("/list_page")
    public AjaxResult listPage(int current, int size) {
        Page<TobaccoSpu> page = new Page<>(current, size);
        Page<TobaccoSpu> list = tobaccoSpuService.page(page);
        if (list != null) {
            return success().put("data", list);
        }
        return error();
    }

    // 返回销售的 spu 类型毛利率前 10 json
    @GetMapping("/type-profit-10")
    public AjaxResult typeProfitTen(int type) {
        PriorityQueue<TobaccoSpu> tobaccoSellProfitTop10 = tobaccoSpuService.getTobaccoSellProfitTop10(type);
        JSONObject source = new JSONObject() {{
            if (type == SpuType.TOBACCO.getCode())
                putOnce("dimensions", new String[]{"product", "卷烟毛利率前十商品(百分比)"});
            else putOnce("dimensions", new String[]{"product", "非卷烟毛利率前十商品(百分比)"});
            List<JSONObject> list = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                if (tobaccoSellProfitTop10.isEmpty()) break;
                JSONObject product = new JSONObject() {{
                    TobaccoSpu poll = tobaccoSellProfitTop10.poll();
                    putOnce("product", poll.getName());
                    if (type == SpuType.TOBACCO.getCode()) putOnce("卷烟毛利率前十商品(百分比)", poll.getProfit());
                    else putOnce("非卷烟毛利率前十商品(百分比)", poll.getProfit());

                }};
                list.add(0, product);

            }
            putOnce("source", list);
        }};
        return success().put("data", source);
    }
}
