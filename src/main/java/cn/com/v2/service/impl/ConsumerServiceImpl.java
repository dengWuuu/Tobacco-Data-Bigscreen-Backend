package cn.com.v2.service.impl;

import cn.com.v2.mapper.ConsumerMapper;
import cn.com.v2.model.Consumer;
import cn.com.v2.service.ConsumerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Wu
 * @date 2024年03月31日 11:03
 */
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper,Consumer> implements ConsumerService{
}
