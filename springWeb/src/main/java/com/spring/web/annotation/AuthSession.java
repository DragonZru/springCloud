package com.spring.web.annotation;

import com.google.common.base.Strings;
import com.spring.web.config.RedisConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Duration;

@Component
public class AuthSession {

    static String AUTH = "auth:";

    private RedisConfig redisConfig;
    private HttpServletRequest request;

    @Inject
    void init(RedisConfig redisConfig, HttpServletRequest request) {
        this.redisConfig = redisConfig;
        this.request = request;
    }

    public void authorize(long id) {
        String sessionId = getSessionID();
        //默认30分钟
        redisConfig.setValue(sessionId, generateAuth(id), Duration.ofSeconds(1800));
    }

    public void deAuthorize() {
        //
        redisConfig.deleteValue(getSessionID());
    }

    public Boolean isAuth() {
        return !Strings.isNullOrEmpty((String) redisConfig.getValue(getSessionID()));
    }

    /**
     * @Author ylli
     * @Description 生成redis-value > auth:id
     */
    public String generateAuth(long id) {
        return new StringBuffer(AUTH).append(id).toString();
    }

    public String getSessionID() {
        HttpSession session = request.getSession();
        System.out.println("session id : " + session.getId());
        return session.getId();
    }
}
