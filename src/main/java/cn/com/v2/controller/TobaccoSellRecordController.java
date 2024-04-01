package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.TobaccoSellRecord;
import cn.com.v2.model.vo.TobaccoSellRecordVo;
import cn.com.v2.service.TobaccoSellRecordService;
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
 * @date 2024年04月01日 16:41
 */
@Api(value = "用户购买记录接口")
@RestController
@RequestMapping("/api/sell_record")
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
}
