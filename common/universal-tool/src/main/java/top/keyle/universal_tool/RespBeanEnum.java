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
    DELETE_ERROR(20015,"删除失败"),
    FIND_PICTURES_ERROR(20025,"该图片不存在"),
    ERROR_LOGIN50008(50008,"Illegal token"),
    ERROR_LOGIN50012(50012,"Other clients logged in"),
    ERROR_LOGIN50014(50014,"Token expired"),
    ERROR_FAILED_TO_SEND_MSM(20020,"发送短信失败"),
    ERROR_THE_FILE_DATA_IS_EMPTY(20021,"文件数据为空"),
    ADD_ERROR(20004,"添加用户失败"),
    COURSE_ADDITION_FAILED(20005, "添加课程信息失败");
    private final Integer code;
    private final String message;
}
