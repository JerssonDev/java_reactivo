package co.com.bancolombia.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
}
