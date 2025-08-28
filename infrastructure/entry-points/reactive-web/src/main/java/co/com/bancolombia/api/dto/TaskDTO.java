package co.com.bancolombia.api.dto;

import co.com.bancolombia.model.Priority;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
}
