package top.keyle.Online_video_learning_system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TMJIE5200
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_pay_log")
public class PaymentInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String orderNo;
    private Date payTime;
    private BigDecimal totalFee;
    private String transactionId;
    private String tradeState;
    private Integer payType;
    private String attr;

    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
