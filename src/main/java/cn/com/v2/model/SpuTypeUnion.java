package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年04月01日 20:53
 */
@TableName("t_spu_type_union")
@Data
public class SpuTypeUnion {

    private String id;
    private String typeId;
    private String spuId;
    private String createTime;
    private String updateTime;
    private String createUser;
    private String updateUser;
}
