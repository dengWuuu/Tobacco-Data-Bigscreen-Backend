package cn.com.v2.service;

import cn.com.v2.model.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
public interface ISysUserService extends IService<SysUser> {

    public Page<SysUser> getUserByNameOrNickname(Page<SysUser> page, String name, String nickname);

}
