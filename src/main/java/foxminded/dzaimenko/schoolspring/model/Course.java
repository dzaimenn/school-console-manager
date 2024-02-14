package foxminded.dzaimenko.schoolspring.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private int courseId;
    private String courseName;
    private String courseDescription;

}