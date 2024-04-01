package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.service.TobaccoSpuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
