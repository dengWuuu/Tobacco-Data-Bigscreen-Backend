package cn.com.v2.controller;

import cn.com.v2.common.config.V2Config;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.Consumer;
import cn.com.v2.service.ConsumerService;
import cn.com.v2.service.ISysFileService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cn.com.v2.common.domain.AjaxResult.error;
import static cn.com.v2.common.domain.AjaxResult.success;

/**
 * 客户接口
 *
 * @author Wu
 * @date 2024年03月31日 11:01
 */
@Api(value = "用户接口")
@RestController
@RequestMapping("/api/consumer")
@Slf4j
public class ConsumerController {
    @Autowired
    private V2Config v2Config;
    @Autowired
    private ConsumerService consumerService;

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询", notes = "查询")
    @GetMapping("/get")
    @ResponseBody
    public AjaxResult get(String id) {
        Consumer consumer = consumerService.getById(id);
        if (consumer == null) {
            return error("无此用户");
        }
        return success().put("data", consumer);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @GetMapping("/list")
    public AjaxResult consumerList() {
        return success().put("data", consumerService.list());
    }

    /**
     * 修改用户
     *
     * @param consumer
     * @return
     */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Consumer consumer) {
        if (StrUtil.isBlank(consumer.getId())) {
            // 用户id为空说明创建用户
            consumerService.save(consumer);
            return success();
        }
        // 不为空则更新
        consumerService.updateById(consumer);
        return success();
    }
    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        consumerService.removeById(id);
        return success();
    }
}
