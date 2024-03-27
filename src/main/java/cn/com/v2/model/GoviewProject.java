package cn.com.v2.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
@TableName("t_goview_project")
@Data
public class GoviewProject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String projectName;

    private Integer state;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    private String createUserId;

    private Integer isDelete;

    private String indexImage;

    private String remarks;

}
