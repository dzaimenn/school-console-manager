package foxminded.dzaimenko.schoolspring.dao.impl;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcGroupDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GroupDao dao;

    @BeforeEach
    void setUp() {
        dao = new JdbcGroupDao(jdbcTemplate);
    }

    @Test
    void testGetAll() {
        List<Group> groups = dao.getAll();
        assertNotNull(groups);
        assertEquals(2, groups.size());
    }

    @Test
    void testCreate() {
        Group newGroup = new Group();
        newGroup.setGroupName("C");
        dao.create(newGroup);

        List<Group> groups = dao.getAll();
        assertNotNull(groups);
        assertEquals(3, groups.size());
    }

    @Test
    void testUpdate() {
        Group group = new Group();
        group.setGroupId(1);
        group.setGroupName("NewGroupName");

        dao.update(group);

        List<Group> allGroups = dao.getAll();

        Group updatedGroup = allGroups.stream()
                .filter(g -> g.getGroupId() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(updatedGroup);
        assertEquals("NewGroupName", updatedGroup.getGroupName());
    }

    @Test
    void testDeleteById() {
        dao.deleteById(1);

        List<Group> groups = dao.getAll();
        assertNotNull(groups);
        assertEquals(1, groups.size());
    }

    @Test
    void testFindGroupsWithMaxStudentCount() {
        int maxStudentCount = 1;
        List<Group> groups = dao.findGroupsWithMaxStudentCount(maxStudentCount);

        assertNotNull(groups);
        assertEquals(1, groups.size());
    }

}