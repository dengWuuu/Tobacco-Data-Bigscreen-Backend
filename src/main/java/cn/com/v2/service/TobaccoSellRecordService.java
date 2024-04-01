package cn.com.v2.service;

import cn.com.v2.model.TobaccoSellRecord;
import cn.com.v2.model.vo.TobaccoSellRecordVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Wu
 * @date 2024年04月01日 16:39
 */
public interface TobaccoSellRecordService extends IService<TobaccoSellRecord> {

    // 获取所有商品购买记录的所有信息
    public List<TobaccoSellRecordVo> getAllSellRecordInfo();
}
