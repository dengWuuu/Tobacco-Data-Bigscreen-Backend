package cn.com.v2.service;

import cn.com.v2.model.TobaccoSellRecord;
import cn.com.v2.model.vo.TobaccoSellRecordVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Wu
 * @date 2024年04月01日 16:39
 */
public interface TobaccoSellRecordService extends IService<TobaccoSellRecord> {

    // 获取所有商品购买记录的所有信息
    public List<TobaccoSellRecordVo> getAllSellRecordInfo();

    // 获取商品销售类型数量
    public Map<Integer, Integer> getSellTypeCount();

    // 获取商品销售类型毛利率
    public Map<Integer, Double> getSellTypeProfit();

    /**
     * 获取销售获取净利润最高的十个商品
     * @param type 类型
     * @return 10个对应的商品
     */
    public PriorityQueue<TobaccoSellRecordVo> getTopTenProfit(int type);

}
