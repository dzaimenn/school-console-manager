package foxminded.dzaimenko.schoolspring.dao;

import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    List<Group> findWithMaxStudentCount(int maxStudentCount);

}