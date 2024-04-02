package cn.com.v2.service;

import cn.com.v2.model.TobaccoSpu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Wu
 * @date 2024年04月01日 17:39
 */
public interface TobaccoSpuService extends IService<TobaccoSpu> {
    /**
     * 获取销售利润最高的10个商品
     *
     * @param type 商品类型 1代表卷烟, 4代表其他
     */
    public PriorityQueue<TobaccoSpu> getTobaccoSellProfitTop10(int type);
}
