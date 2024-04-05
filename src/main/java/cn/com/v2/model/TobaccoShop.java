package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年03月30日 20:52
 */
@TableName("t_shop")
@Data
public class TobaccoShop {
    // main.sql中的sql语句定义出对象属性
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String base;

    private String detail;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String createUser;

    private String updateUser;
}
