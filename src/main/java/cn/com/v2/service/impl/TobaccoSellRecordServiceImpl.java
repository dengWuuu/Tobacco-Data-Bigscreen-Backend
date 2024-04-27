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
            vo.setName(tobaccoSpu.getSkuName());
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

    @Override
    public PriorityQueue<TobaccoSellRecordVo> getTopTenProfit(int type) {
        List<TobaccoSellRecord> tobaccoSellRecords = tobaccoSellRecordMapper.selectList(null);
        Map<String, Integer> map = new HashMap<>();
        for (TobaccoSellRecord tobaccoSellRecord : tobaccoSellRecords) {
            String spuId = tobaccoSellRecord.getSpuId();
            TobaccoSpu tobaccoSpu = tobaccoSpuMapper.selectById(spuId);
            if (type == SpuType.TOBACCO.getCode() && tobaccoSpu.getType() == type) {
                map.put(tobaccoSpu.getSkuName(), map.getOrDefault(tobaccoSpu.getSkuName(), 0) + tobaccoSpu.getPrice() - tobaccoSpu.getPurchasePrice());
            }
            if (type == SpuType.OTHER.getCode() && tobaccoSpu.getType() != SpuType.TOBACCO.getCode()) {
                map.put(tobaccoSpu.getSkuName(), map.getOrDefault(tobaccoSpu.getSkuName(), 0) + tobaccoSpu.getPrice() - tobaccoSpu.getPurchasePrice());
            }
        }
        PriorityQueue<TobaccoSellRecordVo> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(TobaccoSellRecordVo::getProfitCount));
        int size = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (size < 10) {
                TobaccoSellRecordVo vo = new TobaccoSellRecordVo();
                vo.setName(entry.getKey());
                vo.setProfitCount(entry.getValue());
                priorityQueue.add(vo);
                size++;
                continue;
            }
            TobaccoSellRecordVo peek = priorityQueue.peek();
            assert peek != null;
            if (peek.getProfitCount() < entry.getValue()) {
                priorityQueue.poll();
                TobaccoSellRecordVo vo = new TobaccoSellRecordVo();
                vo.setName(entry.getKey());
                vo.setProfitCount(entry.getValue());
                priorityQueue.add(vo);
            }
        }
        return priorityQueue;
    }

    @Override
    public PriorityQueue<TobaccoSellRecordVo> getTopTenReBuy() {
        // 针对每一个用户查询出复购的产品，然后在 map 里记上对应的 cnt
        List<TobaccoSellRecord> tobaccoSellRecords = tobaccoSellRecordMapper.selectList(null);
        // 所有的购买记录存入 map 中格式为 key:consumerID_SpuName value:boolean
        Map<String, Boolean> map = new HashMap<>();
        for (TobaccoSellRecord record : tobaccoSellRecords) {
            String consumerId = record.getConsumerId();
            // 获取商品名字
            String spuId = record.getSpuId();
            TobaccoSpu tobaccoSpu = tobaccoSpuMapper.selectById(spuId);
            String spuName = tobaccoSpu.getSkuName();

            // 如果 map 中没有这个 key 则直接插入
            if (!map.containsKey(consumerId + "_" + spuName)) {
                map.put(consumerId + "_" + spuName, false);
            } else {
                map.put(consumerId + "_" + spuName, true);
            }
        }
        Map<String, Integer> reBuyCntMap = new HashMap<>();
        // 对复购过的产品进行技术
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) {
                String[] split = entry.getKey().split("_");
                String spuName = split[1];
                reBuyCntMap.put(spuName, reBuyCntMap.getOrDefault(spuName, 0) + 1);
            }
        }
        // 挑选出复购前十的产品名字
        PriorityQueue<TobaccoSellRecordVo> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(TobaccoSellRecordVo::getReBuyCount));
        int size = 0;
        for (Map.Entry<String, Integer> entry : reBuyCntMap.entrySet()) {
            if (size < 10) {
                TobaccoSellRecordVo vo = new TobaccoSellRecordVo();
                vo.setName(entry.getKey());
                vo.setReBuyCount(entry.getValue());
                priorityQueue.add(vo);
                size++;
                continue;
            }
            TobaccoSellRecordVo peek = priorityQueue.peek();
            assert peek != null;
            if (peek.getReBuyCount() < entry.getValue()) {
                priorityQueue.poll();
                TobaccoSellRecordVo vo = new TobaccoSellRecordVo();
                vo.setName(entry.getKey());
                vo.setReBuyCount(entry.getValue());
                priorityQueue.add(vo);
            }
        }
        return priorityQueue;
    }
}

