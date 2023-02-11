package top.keyle.online_video_learning_system.entity.vo.collectionVO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TMJIE5200
 * @date 2023-02-11 17:53:32
 * @week 星期六
 * 收藏个数类
 */
@ApiModel(value = "收藏个数类",description = "收藏个数类")
@Data
public class NumberOfFavorites {
    private Date TIME;
    private Integer total;
    private BigDecimal price;
}
