package project.dao;

import java.util.List;

public interface CommentDao {
    List<Comment> getComments(String name);
    Boolean setComment(String title, String comments);
}
