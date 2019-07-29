package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dao.Article;
import project.dao.ArticleDao;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArticleOperations {
    @Autowired
    public ArticleDao articleDaoImp;

    public void setArticleDaoImp(ArticleDao articleDaoImp) {
        this.articleDaoImp = articleDaoImp;
    }

    public ArticleDao getArticleDaoImp() {
        return articleDaoImp;
    }

    public Boolean saveArticle(String name , String author, String simpleDes, MultipartFile file) {
        Article newOne = new Article();
        try {
            newOne.setSimpleDes(simpleDes);
            String articleString = new String(file.getBytes(), "GBK");
            newOne.setArticle(articleString);
            newOne.setName(name);
            newOne.setAuthor(author);
            Date now=new Date();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yy-MM-dd");
            String DateString = sdf2.format(now);
            newOne.setDate(DateString);
            newOne.setColumnIds("");
        }catch (IOException e) {
            return false;
        }
        return articleDaoImp.saveArticle(newOne);
    }

    public List<String> getArticlesTitles() {
        return articleDaoImp.getArticletitles();
    }

    public Article getArticleInfo(String name) {
        return articleDaoImp.getArticleInfos(name).get(0);
    }

    public boolean setArticleColumn(List<String> s, int col){
        return articleDaoImp.setArticleColumn(s, col);
    }

    public  List<String> getArticleNamesByCol(int col) {
        return articleDaoImp.getArticleNamesByCol(col);
    }

    public  List<Map<String, Object>> getArticleContentByCol(int col) {
        List<Map<String, Object>>  all= new ArrayList<Map<String, Object>>() ;
        List<Article> lis = articleDaoImp.getArticleContentByCol(col);
        for(int i = 0; i < lis.size(); i++) {
            Article a = lis.get(i);
            Map<String, Object> resp = new HashMap<String, Object>();
            resp.put("date", a.getDate());
            resp.put("title", a.getName());
            resp.put("author", a.getAuthor());
            String[] pas = a.getArticle().split("\r\n\r\n");
            resp.put("article", pas);
            resp.put("simpleDes",a.getSimpleDes());
            all.add(resp);
        }
        return all;
    }
}
