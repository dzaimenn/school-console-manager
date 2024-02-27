package foxminded.dzaimenko.schoolspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer studentId;
    private Integer groupId;
    private String firstName;
    private String lastName;

}