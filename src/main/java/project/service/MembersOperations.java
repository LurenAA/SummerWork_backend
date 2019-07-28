package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.Members;
import project.dao.MembersDao;

import java.util.List;

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
}
