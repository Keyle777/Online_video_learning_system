package top.keyle.Online_video_learning_system.entity.orderVo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TMJIE5200
 * @date 2023-02-14 20:51:24
 * @week 星期二
 */
@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mobile;
    private String status;
    private String gmtCreate;
    private String gtmModified;
}
