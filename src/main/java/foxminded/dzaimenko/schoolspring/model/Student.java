package foxminded.dzaimenko.schoolspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private int studentId;
    private int groupId;
    private String firstName;
    private String lastName;

}