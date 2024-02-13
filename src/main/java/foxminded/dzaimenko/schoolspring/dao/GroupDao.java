package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Group;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    List<Group> findGroupsWithMaxStudentCount(int maxStudentCount);

}