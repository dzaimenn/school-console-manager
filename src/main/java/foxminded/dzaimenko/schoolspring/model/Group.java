package foxminded.dzaimenko.schoolspring.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    private int groupId;
    private String groupName;

}