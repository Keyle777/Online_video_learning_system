package top.keyle.online_video_learning_system.entry.vo.video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课时基本信息", description = "编辑课时基本信息的表单对象")
@Data
public class VideoInfoForm {
 @ApiModelProperty(value = "视频ID")
 private String id;
 @ApiModelProperty(value = "视频封面")
 private String cover;
 @ApiModelProperty(value = "节点名称")
 private String title;
 @ApiModelProperty(value = "课程ID")
 private String courseId;
 @ApiModelProperty(value = "章节ID")
 private String chapterId;
 @ApiModelProperty(value = "视频资源")
 private String videoSourceId;
 @ApiModelProperty(value = "显示排序")
 private Integer sort;
 @ApiModelProperty(value = "是否可以试听：0默认 1免费")
 private Integer isFree;
 @ApiModelProperty(value = "视频大小")
 private Long size;
 @ApiModelProperty(value = "视频时长（秒）")
 private Double duration;
 @ApiModelProperty(value = "视频状态:见阿里云文档")
 private String status;
 @ApiModelProperty(value = "云服务器上存储的视频文件名称")
 private String videoOriginalName;
}
