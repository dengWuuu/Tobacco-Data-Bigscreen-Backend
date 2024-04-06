package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年04月01日 20:50
 */
@TableName("t_type")
@Data
public class Type {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String createUser;

    private String updateUser;
}
