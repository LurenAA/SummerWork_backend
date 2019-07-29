package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentOperation {
    @Autowired
    public CommentDao commentDaoImp;

    public void setCommentDaoImp(CommentDao commentDaoImp) {
        this.commentDaoImp = commentDaoImp;
    }

    public CommentDao getCommentDaoImp() {
        return commentDaoImp;
    }

    public List<Map<String, Object>> getComments(String name) {
        List<Map<String, Object>>  all= new ArrayList<Map<String, Object>>() ;
        List<Comment> lis = commentDaoImp.getComments(name);
        for(int i = 0; i < lis.size(); i++) {
            Comment a = lis.get(i);
            Map<String, Object> resp = new HashMap<String, Object>();
            resp.put("id", a.getId());
            resp.put("comment", a.getComment());
            resp.put("articleTitle", a.getArticleName());
            all.add(resp);
        }
        return all;
    }

    public Boolean setComment(String title, String comments) {
        return commentDaoImp.setComment(title, comments);
    }
}
