package foxminded.dzaimenko.schoolspring.dao.jdbc.rowmapper;

import foxminded.dzaimenko.schoolspring.model.Group;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Group.builder()
                .id(rs.getInt("group_id"))
                .name(rs.getString("group_name"))
                .build();
    }

}