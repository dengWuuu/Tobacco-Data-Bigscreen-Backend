package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.TobaccoSpu;
import cn.com.v2.service.TobaccoSpuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
