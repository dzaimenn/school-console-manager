package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.GroupDAO;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GroupDAOImpl implements GroupDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Group> findGroupsWithMaxStudentCount(int maxStudentCount) {

        String sql = """
                SELECT g.group_id, g.group_name, COUNT(s.student_id) as student_count 
                FROM groups g 
                LEFT JOIN students s ON g.group_id = s.group_id 
                GROUP BY g.group_id, g.group_name 
                HAVING student_count <= ?
                """;

        return jdbcTemplate.query(sql, new Object[]{maxStudentCount}, new BeanPropertyRowMapper<>(Group.class));
    }

}
