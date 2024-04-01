package cn.com.v2.service.impl;

import cn.com.v2.mapper.TobaccoSellRecordMapper;
import cn.com.v2.mapper.TobaccoSpuMapper;
import cn.com.v2.model.TobaccoSellRecord;
import cn.com.v2.model.TobaccoSpu;
import cn.com.v2.model.vo.TobaccoSellRecordVo;
import cn.com.v2.service.TobaccoSellRecordService;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wu
 * @date 2024年04月01日 16:39
 */
@Service
public class TobaccoSellRecordServiceImpl extends ServiceImpl<TobaccoSellRecordMapper, TobaccoSellRecord> implements TobaccoSellRecordService {
    @Autowired
    TobaccoSellRecordMapper tobaccoSellRecordMapper;
    @Autowired
    TobaccoSpuMapper tobaccoSpuMapper;

    @Override
    public List<TobaccoSellRecordVo> getAllSellRecordInfo() {
        // 获取所有购买记录
        List<TobaccoSellRecord> tobaccoSellRecords = tobaccoSellRecordMapper.selectList(null);

        // 结果构造
        List<TobaccoSellRecordVo> result = new ArrayList<>();
        // 再取出所有 record 中的 spuId
        for (TobaccoSellRecord record : tobaccoSellRecords) {
            String spuId = record.getSpuId();
            // 根据 spuId 查询商品信息
            TobaccoSpu tobaccoSpu = tobaccoSpuMapper.selectById(spuId);

            TobaccoSellRecordVo vo = new TobaccoSellRecordVo();
            vo.setId(record.getId());
            vo.setConsumerId(record.getConsumerId());
            vo.setSpuId(record.getSpuId());
            vo.setName(tobaccoSpu.getName());
            vo.setPrice(tobaccoSpu.getPrice());
            vo.setImage(tobaccoSpu.getImage());
            result.add(vo);
        }
        return result;
    }
}
