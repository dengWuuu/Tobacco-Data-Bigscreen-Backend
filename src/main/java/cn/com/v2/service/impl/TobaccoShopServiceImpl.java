package cn.com.v2.service.impl;

import cn.com.v2.mapper.TobaccoShopMapper;
import cn.com.v2.model.TobaccoShop;
import cn.com.v2.service.TobaccoShopService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wu
 * @date 2024年03月31日 21:17
 */
@Service
public class TobaccoShopServiceImpl extends ServiceImpl<TobaccoShopMapper, TobaccoShop> implements TobaccoShopService {
    @Autowired
    TobaccoShopMapper tobaccoShopMapper;

    @Override
    public Map<String, Integer> getBaseCnt() {
        Map<String, Integer> map = new HashMap<>();
        List<TobaccoShop> tobaccoShops = tobaccoShopMapper.selectList(null);
        for (TobaccoShop tobaccoShop : tobaccoShops) {
            map.put(tobaccoShop.getBase(), map.getOrDefault(tobaccoShop.getBase(), 0) + 1);
        }
        return map;
    }

    @Override
    public Page<TobaccoShop> selectByBaseOrDetail(int page, int size, String base, String detail) {
        QueryWrapper<TobaccoShop> queryWrapper = new QueryWrapper<>();
        Page<TobaccoShop> p = new Page<>(page, size);
        if (!Objects.equals(base, "")) {
            queryWrapper.like("base", base);
        }
        if (!Objects.equals(detail, "")) {
            queryWrapper.or().like("detail", detail);
        }
        return tobaccoShopMapper.selectPage(p, queryWrapper);
    }
}

