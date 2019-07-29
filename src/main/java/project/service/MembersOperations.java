package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.Article;
import project.dao.Members;
import project.dao.MembersDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MembersOperations {
    @Autowired
    private MembersDao membersDaoImp;

    public void setMembersDaoImp(MembersDao membersDaoImp) {
        this.membersDaoImp = membersDaoImp;
    }

    public MembersDao getMembersDaoImp() {
        return membersDaoImp;
    }

    public List<String> getMembersNames() {
        return membersDaoImp.getMembersNames();
    }

    public Members getMemberInof(String name) {
        return membersDaoImp.getMemberInof(name);
    }

    public boolean insertOrUpdate(Members newOne) {
        if(newOne.getId() == -1) {
            return membersDaoImp.insertMember(newOne);
        } else {
            return membersDaoImp.saveMember(newOne);
        }
    }

    public  boolean deleteMembers(int id) {
        return membersDaoImp.deleteMembers(id);
    }

    public  List<Map<String, Object>>  getMembers() {
        List<Map<String, Object>>  all= new ArrayList<Map<String, Object>>() ;
        List<Members> lis = membersDaoImp.getMembers();
        for(int i = 0; i < lis.size(); ++i) {
            Map<String, Object> resp = new HashMap<String, Object>();
            Members a = lis.get(i);
            resp.put("id", a.getId());
            resp.put("photo", a.getPhoto());
            resp.put("event", a.getEvent());
            resp.put("age", a.getAge());
            resp.put("name" , a.getName());
            resp.put("post", a.getPost());
            all.add(resp);
        }
        return all;
    }
}
