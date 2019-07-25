package project.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 暂时没有用数据库储存
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    public static Map<String, String> loginStateTokens;
    public static Map<String, Boolean> loginState;

    static {
        loginStateTokens = new HashMap<String, String>();
        loginState = new HashMap<String, Boolean>();
    }

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        response.setHeader("Access-Control-Expose-Headers","token");
        String token = request.getHeader("token");
        if(token == null) {
            return true;
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            String mapKey = jwt.getAudience().get(0);
            String savedToken = loginStateTokens.get(mapKey);
            if (savedToken == null) {
                loginState.put(mapKey, false);
                return true;
            }
            if (savedToken.equals(token)) {
                loginState.put(mapKey, true);
            }
        }catch (JWTDecodeException er) {
            loginState.clear();
            return true;
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
        AuthenticationInterceptor.loginState.clear();
    }
}
