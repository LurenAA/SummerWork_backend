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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import project.dao.Article;
import project.service.ArticleOperations;
import project.service.LoginState;
import project.service.UserInfoOperations;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * restAPI接口实现
 */
@Controller
@RequestMapping("/request")
@CrossOrigin
public class RestController {
    @Autowired
    private ArticleOperations articleOpera;

    @Autowired
    private UserInfoOperations userInfoOp;

    public UserInfoOperations getUserInfoOp() {
        return userInfoOp;
    }

    public void setUserInfoOp(UserInfoOperations userInfoOp) {
        this.userInfoOp = userInfoOp;
    }

    public void setArticleOpera(ArticleOperations articleOpera) {
        this.articleOpera = articleOpera;
    }

    public ArticleOperations getArticleOpera() {
        return articleOpera;
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

    @RequestMapping(method = RequestMethod.POST,
            value="/upload",produces="application/json;charset=utf-8")
    public ResponseEntity<Boolean> handlePostUploadArticle
            (HttpServletRequest request)
        throws IOException
    {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String name = formDataTranslateToUTF8(multipartRequest.getParameterValues("name")[0]);
        String author = formDataTranslateToUTF8(multipartRequest.getParameterValues("author")[0]);
        String simpleDes = formDataTranslateToUTF8(multipartRequest.getParameterValues("simpleDes")[0]);
        MultipartFile file = multipartRequest.getFile("file");
        Boolean res = articleOpera.saveArticle(name, author, simpleDes, file);
        return new ResponseEntity<Boolean>(res, HttpStatus.OK);
    }

    public static String formDataTranslateToUTF8(String a) throws UnsupportedEncodingException {
        return new String(a.getBytes("ISO-8859-1"), "UTF-8");
    }

    @RequestMapping(method = RequestMethod.GET,
            value="/getArticleTiles")
    public ResponseEntity<List<String>> getArticleTiles
            () {
        List<String> newList = new ArrayList<String>();
        newList = articleOpera.getArticlesTitles();
        return new ResponseEntity<List<String>>(newList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            value="/getArticleInfoByName")
    public ResponseEntity<Map<String, Object>> getArticleInfo
            (@RequestParam("name") String name, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> resp = new HashMap<String, Object>();
        Article a = articleOpera.getArticleInfo(name);
        resp.put("title", a.getName());
        resp.put("author", a.getAuthor());
        resp.put("date", a.getDate());
//        resp.put("article", a.getArticle());
        String[] pas = a.getArticle().split("\r\n\r\n");
        resp.put("article", pas);
        resp.put("simpleDes",a.getSimpleDes());
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }
}
