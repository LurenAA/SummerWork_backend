package project.dao;

import java.util.List;

public interface MembersDao {
    boolean saveMember(Members newOne);
    boolean insertMember(Members newOne);
    List<String> getMembersNames();
    Members getMemberInof(String name);
    boolean deleteMembers(int id);
    List<Members> getMembers();
}
