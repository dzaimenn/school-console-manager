package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.GroupDAO;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_GROUPS_WITH_MAX_STUDENT_COUNT = """
            SELECT g.group_id, g.group_name, COUNT(s.student_id) as student_count
            FROM groups g
            LEFT JOIN students s ON g.group_id = s.group_id
            GROUP BY g.group_id, g.group_name
            HAVING COUNT(s.student_id) <= ?
            """;

    public GroupDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findGroupsWithMaxStudentCount(int maxStudentCount) {
        return jdbcTemplate.query(SQL_FIND_GROUPS_WITH_MAX_STUDENT_COUNT, new Object[]{maxStudentCount}, new BeanPropertyRowMapper<>(Group.class));
    }

}