package top.keyle.universal_tool;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * todo :声明为异常处理器类
 * @date 2022-11-09 20:09:40
 * @version 1.0
 * @author TMJIE5200
 */
@SuppressWarnings("all")
@RestControllerAdvice
public class GlobalExceptionHandler {
    //进行具体异常分类处理
    //拦截所以异常进行抓取 处理
    @ExceptionHandler(Exception.class)
    public RespBean exceptionHandle(Exception exception) {
        //判断异常类型
        if (exception instanceof GlobalException) {
            //拦截的是自定义的异常
            GlobalException globalException = (GlobalException) exception;
            // 参数为 全局异常的枚举
            return RespBean.error(globalException.getRespBeanEnum());
        } else if (exception instanceof BindException) {
            //拦截的是validator绑定的异常
            BindException bindException = (BindException) exception;
            //定义返回为绑定错误
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            //设置数据为绑定消息
            respBean.setMessage(
                    RespBeanEnum.BIND_ERROR.getMessage()
                            + ":::" + bindException.getBindingResult()
                            .getAllErrors()
                            .get(0)
                            .getDefaultMessage()
            );
            return respBean;
        }
        //不属于那两个异常 就返回这
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
