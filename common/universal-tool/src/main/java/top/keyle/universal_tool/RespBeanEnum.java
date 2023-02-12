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
    SUCCESS2(200,"SUCESS"),
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
    COURSE_ADDITION_FAILED(20005, "添加课程信息失败"),
    FAILED_TO_MODIFY_COURSE(20006,"修改课程信息失败"),

    VIDEO_PRESENCE_PROMPT(20007,"该分章节下存在视频课程，请先删除视频课程"),
    DELETING_VIDEO_FAILED(20008,"删除视频失败"),
    LESSON_SAVING_FAILED(20009,"课时信息保存失败"),
    THE_DATA_DOES_NOT_EXIST(20010,"数据不存在"),
    THE_VIDEO_ID_IS_EMPTY(20011,"videoId为空"),
    THERE_ARE_SUBSECTIONS_UNDER_THIS_SECTION(20012,"此章节下有小节，先删除"),
    FAILED_TO_GET_CREDENTIALS(20013,"获取凭证失败"),
    CONNECTION_TIMED_OUT(20014,"连接超时"),
    LOGIN_FAILED(20015,"登录失败"),
    REGISTRATION_FAILED(20016,"注册失败"),
    USERS_WITH_THE_SAME_PHONE_NUMBER(20017,"存在相同手机号用户"),
    CAPTCHA_ERROR(20018,"验证码不正确"),
    THE_PASSWORD_IS_INCORRECT(20019,"密码不正确"),
    THE_ACCOUNT_IS_NOT_REGISTERED(20020,"该账号未注册"),
    THE_MOBILE_PHONE_NUMBER_IS_NOT_LINKED(20021,"该邮箱未绑定手机号"),
    NOT_LOGGED_IN(20022,"请登录后重试");
    private final Integer code;
    private final String message;
}
