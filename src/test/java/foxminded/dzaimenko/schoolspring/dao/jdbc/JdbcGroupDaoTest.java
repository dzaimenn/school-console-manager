package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"classpath:/sql/drop_test_tables.sql",
                "classpath:/db/migration/V1__Create_Tables.sql",
                "classpath:/sql/insert_test_data.sql"},

        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@ComponentScan(basePackages = "foxminded.dzaimenko.schoolspring.dao")
class JdbcGroupDaoTest {

    @Autowired
    private GroupDao dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Group> prepareExpectedGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(Group.builder().id(1).name("A").build());
        expectedGroups.add(Group.builder().id(2).name("B").build());

        return expectedGroups;
    }

    @Test
    void testGetAll() {
        List<Group> expected = prepareExpectedGroups();
        List<Group> actual = dao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {
        Group newGroup = Group.builder()
                .name("C")
                .build();

        dao.create(newGroup);

        int expected = 1;
        int actualName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "groups",
                "group_name = 'C'");

        assertEquals(expected, actualName);
    }

    @Test
    void testUpdate() {
        Group group = Group.builder()
                .id(1)
                .name("NewGroupName")
                .build();

        dao.update(group);

        int expected = 1;
        int actualName = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "groups",
                "group_id = 1 AND group_name = 'NewGroupName'");

        assertEquals(expected, actualName);
    }

    @Test
    void testDelete() {
        int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups") - 1;

        dao.deleteById(1);
        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");

        assertEquals(expected, actual);
    }

    @Test
    void testFindGroupsWithMaxStudentCount() {
        int maxStudentCount = 1;
        List<Group> groups = dao.findWithMaxStudentCount(maxStudentCount);

        assertEquals(1, groups.size());
    }

}