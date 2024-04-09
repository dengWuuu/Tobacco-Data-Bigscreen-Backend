package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.SpuType;
import cn.com.v2.model.TobaccoSpu;
import cn.com.v2.model.Type;
import cn.com.v2.service.TobaccoSpuService;
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
    public AjaxResult list(int page, int size, String skuName) {
        Page<TobaccoSpu> p = new Page<>(page, size);
        // 不为空 条件查询
        JSONObject data = new JSONObject();
        if (skuName != null && !Objects.equals(skuName, "")) {
            // 条件查询
            Page<TobaccoSpu> list = tobaccoSpuService.selectBySkuName(p, skuName);
            data.putOnce("total", list.getTotal());
            data.putOnce("spus", list.getRecords());
            data.putOnce("current", list.getCurrent());
            data.putOnce("size", list.getSize());
            return success().put("data", data);
        }
        Page<TobaccoSpu> pageList = tobaccoSpuService.page(p);
        data.putOnce("total", pageList.getTotal());
        data.putOnce("spus", pageList.getRecords());
        data.putOnce("current", pageList.getCurrent());
        data.putOnce("size", pageList.getSize());
        return success().put("data", data);
    }

    // 保存或者更新商品
    @PostMapping("/update")
    public AjaxResult update(@RequestBody TobaccoSpu spu) {
        // id不为空是更新
        if (spu.getId() != null && !spu.getId().equals("")) {
            boolean update = tobaccoSpuService.updateById(spu);
            if (update) {
                return success();
            }
            return error();
        }
        // id为空是插入
        boolean save = tobaccoSpuService.save(spu);
        if (save) {
            return success();
        }
        return error();
    }

    // 删除接口
    @DeleteMapping("/remove")
    public AjaxResult remove(String id) {
        boolean remove = tobaccoSpuService.removeById(id);
        if (remove) {
            return success();
        }
        return error();
    }

    // 批量删除接口
    @DeleteMapping("/remove-batch")
    public AjaxResult removeBatch(String[] ids) {
        boolean remove = tobaccoSpuService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return success();
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
                    putOnce("product", poll.getSkuName());
                    if (type == SpuType.TOBACCO.getCode()) putOnce("卷烟毛利率前十商品(百分比)", poll.getProfit());
                    else putOnce("非卷烟毛利率前十商品(百分比)", poll.getProfit());

                }};
                list.add(0, product);

            }
            putOnce("source", list);
        }};
        return success().put("data", source);
    }

    @GetMapping("/listing-count")
    public AjaxResult listingCount() {
        Map<Integer, Integer> map = tobaccoSpuService.getSpuListingCount();
        JSONObject source = new JSONObject() {{
            putOnce("dimensions", new String[]{"product", "门店上架规格数量"});
            List<JSONObject> list = new LinkedList<>();
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                JSONObject product = new JSONObject() {{
                    putOnce("product", Objects.requireNonNull(SpuType.getByCode(entry.getKey())).getName());
                    putOnce("门店上架规格数量", entry.getValue());
                }};
                list.add(product);
            }
            putOnce("source", list);
        }};
        return success().put("data", source);
    }
}
