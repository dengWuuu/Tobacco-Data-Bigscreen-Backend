package cn.com.v2.service.impl;

import cn.com.v2.model.SysUser;
import cn.com.v2.mapper.SysUserMapper;
import cn.com.v2.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Page<SysUser> getUserByNameOrNickname(Page<SysUser> page, String name, String nickname) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.equals("")) {
            queryWrapper.like("username", name);
        }
        if (nickname != null && !nickname.equals("")) {
            queryWrapper.or().like("nickname", nickname);
        }
        return sysUserMapper.selectPage(page, queryWrapper);
    }
}
