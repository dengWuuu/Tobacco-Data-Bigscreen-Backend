package cn.com.v2.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Wu
 * @date 2024年04月01日 16:47
 */
@Data
public class TobaccoSellRecordVo {
    private String id;
    private String consumerId;
    private String spuId;
    private String name;
    private Integer price;
    private String image;
    private Integer profitCount;
    private Integer reBuyCount;

}
