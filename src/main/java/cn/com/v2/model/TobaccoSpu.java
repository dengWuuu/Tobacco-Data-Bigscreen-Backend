package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年03月30日 20:46
 */
@TableName("t_consumer")
@Data
public class TobaccoSpu {
    // main.sql中的sql语句定义出对象属性
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String skuName;

    private String price;

    private String image;

    private String status;

    private Integer num;

    private String createTime;

    private String updateTime;

    private String createUser;

    private String updateUser;
}
