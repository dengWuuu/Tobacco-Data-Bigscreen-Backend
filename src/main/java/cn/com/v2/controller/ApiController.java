package cn.com.v2.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.com.v2.common.base.BaseController;
import cn.com.v2.common.domain.AjaxResult;
import cn.com.v2.model.SysUser;
import cn.com.v2.service.ISysUserService;
import cn.com.v2.util.SaTokenUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/goview/sys")
public class ApiController  extends BaseController {
	@Autowired
	private ISysUserService iSysUserService;

	@ApiOperation(value = "登陆", notes = "登陆")
	@PostMapping("/login")
	@ResponseBody
	public AjaxResult APIlogin(@RequestBody SysUser user, HttpServletRequest request) {

		// 判断是否登陆
		if (StpUtil.isLogin()) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userinfo", SaTokenUtil.getUser());
			map.put("token", StpUtil.getTokenInfo());
			return success().put("data", map);
		} else {
			if (StrUtil.isNotBlank(user.getUsername()) && StrUtil.isNotBlank(user.getPassword())) {
				SysUser sysUser = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, user.getUsername()).eq(SysUser::getPassword, SecureUtil.md5(user.getPassword())).last("LIMIT 1"));
				if (sysUser != null) {
					StpUtil.login(sysUser.getId());
					SaTokenUtil.setUser(sysUser);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userinfo", sysUser);
					map.put("token", StpUtil.getTokenInfo());

					return success().put("data", map);
				} else {
					return error(500, "账户或者密码错误");
				}
			} else {
				return error(500, "账户密码不能为空");
			}
		}

	}
	
	
	@ApiOperation(value = "登陆", notes = "登陆")
	@GetMapping("/logout")
	@ResponseBody
	public AjaxResult logout() {

		// 判断是否登陆
		StpUtil.logout();

		return success();

	}
	
	
	@ApiOperation(value = "获取oss地址", notes = "获取oss地址")
	@GetMapping("/getOssInfo")
	@ResponseBody
	public AjaxResult getOssInfo() {

		return success();

	}

}
