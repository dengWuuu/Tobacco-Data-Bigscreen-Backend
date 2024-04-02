package cn.com.v2.service;

import cn.com.v2.model.TobaccoShop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author Wu
 * @date 2024年03月31日 21:17
 */
public interface TobaccoShopService extends IService<TobaccoShop> {
    public Map<String, Integer> getBaseCnt();
}
