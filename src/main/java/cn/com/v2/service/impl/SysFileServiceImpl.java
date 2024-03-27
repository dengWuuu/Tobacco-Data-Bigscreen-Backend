package cn.com.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.com.v2.mapper.SysFileMapper;
import cn.com.v2.model.SysFile;
import cn.com.v2.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fc
 * @since 2022-12-22
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {
	@Autowired
	private SysFileMapper sysFileMapper;
	
	@Override
	public SysFile selectByExamplefileName(String filename) {
		SysFile sysFile=sysFileMapper.selectOne(new LambdaQueryWrapper<SysFile>().eq(SysFile::getFileName, filename));
        return sysFile;
	}

}
