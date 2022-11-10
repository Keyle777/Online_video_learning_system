package top.keyle.universal_tool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * todo :定义异常
 * @author TMJIE5200
 * @date 2022-10-05 20:12:57
 * @week 星期三
 */
@ToString
@AllArgsConstructor
@Getter
@SuppressWarnings("all")
public enum RespBeanEnum {
    ERROR(20001,"服务端异常"),
    SUCCESS(20000,"SUCCESS"),
    LOGIN_ERROR(40000,"用户名或密码不正确"),
    MOBILE_ERROR(40001,"手机号码不正确"),
    BIND_ERROR(40002,"绑定失败"),
    SELECT_ERROR(40012,"查询失败"),
    UPDATE_ERROR(40012,"修改失败"),
    ADD_ERROR(20004,"添加用户失败");
    private final Integer code;
    private final String message;
}
