package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dao.Article;
import project.dao.ArticleDao;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
