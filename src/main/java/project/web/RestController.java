package project.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import project.service.LoginState;
import project.service.UserInfoOperations;

import javax.print.DocFlavor;
import java.util.*;

/**
 * restAPI接口实现
 */
@Controller
@RequestMapping("/request")
@CrossOrigin
public class RestController {
    @Autowired
    private UserInfoOperations userInfoOp;

    public UserInfoOperations getUserInfoOp() {
        return userInfoOp;
    }

    public void setUserInfoOp(UserInfoOperations userInfoOp) {
        this.userInfoOp = userInfoOp;
    }

    /**
     * 用户登陆
     * @param inf
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
    consumes = "application/json",
    value="/passwd")
    public ResponseEntity<Map<String, String>> CheckBackEndPass
            (@RequestBody Map<String, String> inf) {
        Map<String, String> resp = new HashMap<String, String>();
        if(AuthenticationInterceptor.loginState.size() != 0) {
            Set<String> key =   AuthenticationInterceptor.loginState.keySet();
            String account = key.iterator().next();
            String userName = userInfoOp.getUserName(account);
            resp.put("username", userName);
            resp.put("state", LoginState.success.getLoginState() + "");
            resp.put("account", account);
            return new ResponseEntity<Map<String, String>>(resp,HttpStatus.OK);
        }
        Map<String, Object> loginMap = userInfoOp.login(inf.get("account"), inf.get("passwd"));
        LoginState loginStat = ((LoginState)loginMap.get("state"));
        resp.put("state", loginStat.getLoginState() + "");
        resp.put("account", inf.get("account"));
        resp.put("username", (String)loginMap.get("username"));
        if(loginStat == LoginState.success) {
            String token;
            token = JWT.create().withAudience(inf.get("account"))
                    .sign(Algorithm.HMAC256(inf.get("passwd")));
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("token", token);
            AuthenticationInterceptor.loginStateTokens.put(inf.get("account"), token);
            return new ResponseEntity<Map<String, String>>(resp,headers,HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }
}
