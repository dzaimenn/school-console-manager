package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

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
    private GroupDao groupDao;

    private List<Group> prepareExpectedGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(Group.builder().id(1).name("A").build());
        expectedGroups.add(Group.builder().id(2).name("B").build());

        return expectedGroups;
    }

    @Test
    void testGetAll() {
        List<Group> expected = prepareExpectedGroups();
        List<Group> actual = groupDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testCreate() {
        Group newGroup = new Group();
        newGroup.setName("C");
        groupDao.create(newGroup);

        List<Group> groups = groupDao.getAll();

        assertEquals(3, groups.size());
    }

    @Test
    void testUpdate() {
        Group group = new Group();
        group.setId(1);
        group.setName("NewGroupName");

        groupDao.update(group);

        List<Group> allGroups = groupDao.getAll();

        Group updatedGroup = allGroups.stream()
                .filter(g -> g.getId() == 1)
                .findFirst()
                .orElse(null);

        assertEquals("NewGroupName", updatedGroup.getName());
    }

    @Test
    void testDeleteById() {
        groupDao.deleteById(1);

        List<Group> groups = groupDao.getAll();

        assertEquals(1, groups.size());
    }

    @Test
    void testFindGroupsWithMaxStudentCount() {
        int maxStudentCount = 1;
        List<Group> groups = groupDao.findWithMaxStudentCount(maxStudentCount);

        assertEquals(1, groups.size());
    }

}