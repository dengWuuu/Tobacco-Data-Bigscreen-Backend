package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年03月30日 21:14
 */
@TableName("t_sell_record")
@Data
public class TobaccoSellRecord {
    // main.sql中的sql语句定义出对象属性
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String consumer_id;

    private String spu_id;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT)
    private String updateTime;

    private String createUser;

    private String updateUser;
}
