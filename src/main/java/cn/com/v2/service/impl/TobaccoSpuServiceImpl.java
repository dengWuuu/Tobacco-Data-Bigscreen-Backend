package cn.com.v2.service.impl;

import cn.com.v2.mapper.TobaccoSpuMapper;
import cn.com.v2.model.SpuType;
import cn.com.v2.model.TobaccoSellRecord;
import cn.com.v2.model.TobaccoSpu;
import cn.com.v2.service.TobaccoSpuService;
import cn.com.v2.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Wu
 * @date 2024年04月01日 17:40
 */
@Service
public class TobaccoSpuServiceImpl extends ServiceImpl<TobaccoSpuMapper, TobaccoSpu> implements TobaccoSpuService {
    @Autowired
    TobaccoSpuMapper tobaccoSpuMapper;

    @Override
    public PriorityQueue<TobaccoSpu> getTobaccoSellProfitTop10(int type) {
        // 用来记录毛利率的 map
        Map<String, Double> ProfitMap = new HashMap<>();
        List<TobaccoSpu> tobaccoSpus = tobaccoSpuMapper.selectList(null);
        for (TobaccoSpu tobaccoSpu : tobaccoSpus) {
            double profit = (double) (tobaccoSpu.getPrice() - tobaccoSpu.getPurchasePrice()) / (double) tobaccoSpu.getPurchasePrice();
            if (tobaccoSpu.getType() == SpuType.TOBACCO.getCode() && type == SpuType.TOBACCO.getCode()) {
                tobaccoSpu.setProfit(NumberUtil.halfUp(profit));
                ProfitMap.put(tobaccoSpu.getId(), NumberUtil.halfUp(profit));
                continue;
            }
            if (tobaccoSpu.getType() != SpuType.TOBACCO.getCode() && type == SpuType.OTHER.getCode()) {
                tobaccoSpu.setProfit(NumberUtil.halfUp(profit));
                ProfitMap.put(tobaccoSpu.getId(), NumberUtil.halfUp(profit));
            }
        }

        // 接下来用小顶堆筛选出前 10 的毛利率代表
        PriorityQueue<TobaccoSpu> priorityQueue = new PriorityQueue<>(10, new Comparator<TobaccoSpu>() {
            @Override
            public int compare(TobaccoSpu o1, TobaccoSpu o2) {
                return ProfitMap.get(o1.getId()).compareTo(ProfitMap.get(o2.getId()));
            }
        });
        int size = 0;
        for (TobaccoSpu tobaccoSpu : tobaccoSpus) {
            if (!ProfitMap.containsKey(tobaccoSpu.getId())) continue;
            if (size < 10) {
                priorityQueue.add(tobaccoSpu);
                size++;
                continue;
            }
            if (ProfitMap.get(tobaccoSpu.getId()) > ProfitMap.get(priorityQueue.peek().getId())) {
                priorityQueue.poll();
                priorityQueue.add(tobaccoSpu);
            }

        }
        return priorityQueue;
    }

    @Override
    public Map<Integer, Integer> getSpuListingCount() {
        Map<Integer, Integer> map = new HashMap<>();
        List<TobaccoSpu> tobaccoSpus = tobaccoSpuMapper.selectList(null);
        for (TobaccoSpu tobaccoSpu : tobaccoSpus) {
            if (Objects.equals(tobaccoSpu.getStatus(), "1"))
                map.put(tobaccoSpu.getType(), map.getOrDefault(tobaccoSpu.getType(), 0) + 1);
        }
        return map;
    }

    @Override
    public Page<TobaccoSpu> selectBySkuName(Page<TobaccoSpu> page, String skuName) {
        QueryWrapper<TobaccoSpu> queryWrapper = new QueryWrapper<>();
        if (skuName != null && !Objects.equals(skuName, "")) {
            queryWrapper.like("name", skuName);
        }
        return tobaccoSpuMapper.selectPage(page, queryWrapper);
    }
}
