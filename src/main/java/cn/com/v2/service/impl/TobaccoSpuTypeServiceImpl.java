package cn.com.v2.service.impl;

import cn.com.v2.mapper.TobaccoSpuTypeMapper;
import cn.com.v2.model.Type;
import cn.com.v2.service.TobaccoSpuTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Wu
 * @date 2024年04月06日 16:27
 */
@Service
public class TobaccoSpuTypeServiceImpl extends ServiceImpl<TobaccoSpuTypeMapper, Type> implements TobaccoSpuTypeService {
    @Override
    public Page<Type> selectByName(Page<Type> p, String name) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return baseMapper.selectPage(p, queryWrapper);
    }
}
