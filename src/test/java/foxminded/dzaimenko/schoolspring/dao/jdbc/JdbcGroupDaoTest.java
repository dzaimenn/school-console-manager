package foxminded.dzaimenko.schoolspring.dao.jdbc;

import foxminded.dzaimenko.schoolspring.dao.GroupDao;
import foxminded.dzaimenko.schoolspring.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"src/main/resources/db/migration/V1__Create_Tables.sql", "sql/insert_test_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JdbcGroupDaoTest {

    @Autowired
    private GroupDao groupDao;

    @Test
    void testGetAll() {
        List<Group> groups = groupDao.getAll();
        assertEquals(2, groups.size());
    }

    @Test
    void testCreate() {
        Group newGroup = new Group();
        newGroup.setName("C");
        groupDao.create(newGroup);

        List<Group> groups = groupDao.getAll();
        assertNotNull(groups);
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

        assertNotNull(updatedGroup);
        assertEquals("NewGroupName", updatedGroup.getName());
    }

    @Test
    void testDeleteById() {
        groupDao.deleteById(1);

        List<Group> groups = groupDao.getAll();
        assertNotNull(groups);
        assertEquals(1, groups.size());
    }

    @Test
    void testFindGroupsWithMaxStudentCount() {
        int maxStudentCount = 1;
        List<Group> groups = groupDao.findWithMaxStudentCount(maxStudentCount);

        assertNotNull(groups);
        assertEquals(1, groups.size());
    }

}