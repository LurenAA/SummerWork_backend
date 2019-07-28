package project.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * dao层的config文件
 */
@Configuration
public class DaoConfig {
    @Bean
    UserInfoDao userInfoDaoImp(NamedParameterJdbcOperations jdbc) {
        UserInfoDaoImp newUserDaoImp = new UserInfoDaoImp();
        newUserDaoImp.setJdbc(jdbc);
        return newUserDaoImp;
    }

    @Bean
    ArticleDao articleDaoImp(NamedParameterJdbcOperations jdbc) {
        ArticleDaoImp newOne = new ArticleDaoImp();
        newOne.setJdbc(jdbc);
        return newOne;
    }

    @Bean
    MembersDao membersDaoImp(NamedParameterJdbcOperations jdbc) {
        MembersDaoImp newOne = new MembersDaoImp();
        newOne.setJdbc(jdbc);
        return newOne;
    }
}
