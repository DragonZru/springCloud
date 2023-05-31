package com.spring.web.annotation;

import com.spring.web.exception.GlobalException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.reflect.Method;

@Aspect
@Component
public class AuthAspect {

    private AuthSession authSession;

    @Inject
    void init(AuthSession authSession) {
        this.authSession = authSession;
    }


    @Pointcut("@annotation(com.spring.web.annotation.Auth)")
    public void guard() {
    }

    @Before("guard()")
    private void doGuard(JoinPoint joinPoint)
            throws GlobalException {
        Auth auth = getAuth(joinPoint);

        if (!authSession.isAuth()) {
            throw new GlobalException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

//        //TODO 权限检测.   这里 0 随便先写个
//        if (!Arrays.asList(auth.values()).contains(0)) {
//            throw new GlobalException("权限不足.", HttpStatus.FORBIDDEN);
//        }
    }

    private Auth getAuth(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //先获取方法上的注解
        Auth auth = method.getAnnotation(Auth.class);
        if (auth == null) {
            //得到类上的访问注解
            auth = joinPoint.getTarget().getClass().getAnnotation(Auth.class);
        }
        return auth;
    }
}
