package top.rogermaster.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @Author: Roger
 * @description: Controller日志埋点
 * @date: 2020/8/25 10:16 下午
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {


    /**
     * 对包下所有的controller结尾的类的所有方法增强
     */
    private final String executeExpr = "execution(* top.rogermaster.common.config.ControllerAdvice.*(..))";

    /**
     * @param joinPoint:
     * @Author: TheBigBlue
     * @Description: 环绕通知，拦截controller，输出请求参数、响应内容和响应时间
     * @Date: 2019/6/17
     * @Return:
     **/
    @Around(executeExpr)
    public Object processLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        log.info("请求结束，controller的返回值是 " + JSONObject.wrap(result).toString());
        return result;
    }
}
