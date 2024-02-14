package foxminded.dzaimenko.schoolspring.model;

import lombok.*;

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