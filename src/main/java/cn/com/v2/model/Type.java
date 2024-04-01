package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年04月01日 20:50
 */
@TableName("t_type")
@Data
public class Type {
    private String id;
    private String name;
    private String createTime;
    private String updateTime;
    private String createUser;
    private String updateUser;
}
