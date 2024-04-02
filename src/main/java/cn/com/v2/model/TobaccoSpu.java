package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年03月30日 20:46
 */
@TableName("t_spu")
@Data
public class TobaccoSpu {
    // main.sql中的sql语句定义出对象属性
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("sku_name")
    private String name;

    // 售价
    private Integer price;

    // 进价
    private Integer purchasePrice;

    // 类型
    private Integer type;

    private String image;

    private String status;

    private Integer num;

    private String createTime;

    private String updateTime;

    private String createUser;

    private String updateUser;

    @TableField(exist = false)
    private Double profit;
}
