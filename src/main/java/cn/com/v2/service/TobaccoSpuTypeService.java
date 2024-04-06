package cn.com.v2.service;

import cn.com.v2.model.Type;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Wu
 * @date 2024年04月06日 16:27
 */
public interface TobaccoSpuTypeService extends IService<Type> {
    public Page<Type> selectByName(Page<Type> p, String name);
}
