package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGroupDao implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_GROUPS_WITH_MAX_STUDENT_COUNT = """
            SELECT g.group_id, g.group_name, COUNT(s.student_id) as student_count
            FROM groups g
            LEFT JOIN students s ON g.group_id = s.group_id
            GROUP BY g.group_id, g.group_name
            HAVING COUNT(s.student_id) <= ?
            """;

    private static final String SQL_CREATE_GROUP = "INSERT INTO groups (group_name) VALUES (?)";

    private static final String SQL_SELECT_ALL_GROUPS = "SELECT * FROM groups";

    private static final String SQL_UPDATE_GROUP = "UPDATE groups SET group_name = ? WHERE group_id = ?";

    private static final String SQL_DELETE_GROUP_BY_ID = "DELETE FROM groups WHERE group_id = ?";

    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findGroupsWithMaxStudentCount(int maxStudentCount) {
        return jdbcTemplate.query(SQL_FIND_GROUPS_WITH_MAX_STUDENT_COUNT, new Object[]{maxStudentCount}, new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public void createGroup(Group group) {
        jdbcTemplate.update(SQL_CREATE_GROUP, group.getGroupName());
    }

    @Override
    public List<Group> getAllGroups() {
        return jdbcTemplate.query(SQL_SELECT_ALL_GROUPS, BeanPropertyRowMapper.newInstance(Group.class));
    }

    @Override
    public void updateGroup(Group group) {
        jdbcTemplate.update(SQL_UPDATE_GROUP, group.getGroupId(), group.getGroupName());
    }

    @Override
    public void deleteGroupById(int groupId) {
        jdbcTemplate.update(SQL_DELETE_GROUP_BY_ID, groupId);
    }

}