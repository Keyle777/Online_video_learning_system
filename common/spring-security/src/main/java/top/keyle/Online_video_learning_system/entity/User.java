package top.keyle.Online_video_learning_system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户实体类")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "管理员账号")
	private String username;

	@ApiModelProperty(value = "管理员密码")
	private String password;

	@ApiModelProperty(value = "管理员昵称")
	private String nickName;

	@ApiModelProperty(value = "管理员头像")
	private String salt;

	@ApiModelProperty(value = "用户签名")
	private String token;

}



