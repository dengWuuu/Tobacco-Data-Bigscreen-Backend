package cn.com.v2.service.impl;

import cn.com.v2.mapper.TobaccoSellRecordMapper;
import cn.com.v2.mapper.TobaccoSpuMapper;
import cn.com.v2.model.SpuType;
import cn.com.v2.model.TobaccoSellRecord;
import cn.com.v2.model.TobaccoSpu;
import cn.com.v2.model.vo.TobaccoSellRecordVo;
import cn.com.v2.service.TobaccoSellRecordService;
import cn.com.v2.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

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

    @Override
    public Map<Integer, Integer> getSellTypeCount() {
        List<TobaccoSellRecord> tobaccoSellRecords = tobaccoSellRecordMapper.selectList(null);
        Map<Integer, Integer> map = new HashMap<>();
        for (TobaccoSellRecord tobaccoSellRecord : tobaccoSellRecords) {
            String spuId = tobaccoSellRecord.getSpuId();
            TobaccoSpu tobaccoSpu = tobaccoSpuMapper.selectById(spuId);
            map.put(tobaccoSpu.getType(), map.getOrDefault(tobaccoSpu.getType(), 0) + tobaccoSpu.getPrice());
        }
        return map;
    }

    @Override
    public Map<Integer, Double> getSellTypeProfit() {
        Map<Integer, Double> profitMap = new HashMap<>();
        List<TobaccoSellRecord> tobaccoSellRecords = tobaccoSellRecordMapper.selectList(null);
        int totalTobaccoPrice = 0, totalTobaccoCost = 0;
        int otherPrice = 0, otherCost = 0;
        for (TobaccoSellRecord tobaccoSellRecord : tobaccoSellRecords) {
            String spuId = tobaccoSellRecord.getSpuId();
            TobaccoSpu tobaccoSpu = tobaccoSpuMapper.selectById(spuId);
            if (tobaccoSpu.getType() == SpuType.TOBACCO.getCode()) {
                totalTobaccoPrice += tobaccoSpu.getPrice();
                totalTobaccoCost += tobaccoSpu.getPurchasePrice();
                continue;
            }
            otherPrice += tobaccoSpu.getPrice();
            otherCost += tobaccoSpu.getPurchasePrice();
        }
        double tobaccoProfit = (double) (totalTobaccoPrice - totalTobaccoCost) / (double) totalTobaccoCost;
        double otherProfit = (double) (otherPrice - otherCost) / (double) otherCost;
        profitMap.put(SpuType.TOBACCO.getCode(), NumberUtil.halfUp(tobaccoProfit));
        profitMap.put(SpuType.OTHER.getCode(), NumberUtil.halfUp(otherProfit));
        return profitMap;
    }
}

