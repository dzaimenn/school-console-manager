package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Group;

import java.util.Map;

public interface GroupDAO {

    Map<Group, Integer> findGroupsByMinStudentsCount();

}
