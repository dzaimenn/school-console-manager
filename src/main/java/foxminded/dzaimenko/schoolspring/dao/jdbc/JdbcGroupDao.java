package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.dao.jdbc.rowmapper.GroupRowMapper;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGroupDao implements GroupDao {

    private static final String SQL_SELECT_ALL_GROUPS = "SELECT * FROM groups";

    private static final String SQL_CREATE_GROUP = "INSERT INTO groups (group_name) VALUES (?) RETURNING group_id";

    private static final String SQL_UPDATE_GROUP = "UPDATE groups SET group_name = ? WHERE group_id = ?";

    private static final String SQL_SELECT_GROUP_BY_ID = "SELECT * FROM groups WHERE group_id = ?";

    private static final String SQL_DELETE_GROUP_BY_ID = "DELETE FROM groups WHERE group_id = ?";

    private static final String SQL_FIND_GROUPS_WITH_MAX_STUDENT_COUNT = """
            SELECT g.group_id, g.group_name, COUNT(s.student_id) as student_count
            FROM groups g
            LEFT JOIN students s ON g.group_id = s.group_id
            GROUP BY g.group_id, g.group_name
            HAVING COUNT(s.student_id) <= ?
            """;

    private final JdbcTemplate jdbcTemplate;
    private final GroupRowMapper groupRowMapper;

    public JdbcGroupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupRowMapper = groupRowMapper;
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_GROUPS, groupRowMapper);
    }

    @Override
    public Integer create(Group group) {
       return jdbcTemplate.queryForObject(SQL_CREATE_GROUP, Integer.class, group.getName());
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update(SQL_UPDATE_GROUP, group.getName(), group.getId());
    }

    @Override
    public Optional<Group> findById(int id) {
        Group group = jdbcTemplate.queryForObject(SQL_SELECT_GROUP_BY_ID, groupRowMapper, id);
        return Optional.ofNullable(group);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(SQL_DELETE_GROUP_BY_ID, id);
    }

    @Override
    public List<Group> findWithMaxStudentCount(int maxStudentCount) {
        return jdbcTemplate.query(SQL_FIND_GROUPS_WITH_MAX_STUDENT_COUNT, new Object[]{maxStudentCount}, groupRowMapper);
    }

}