package co.com.bancolombia.r2dbc.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskEntity {

    @Id
    @Column("task_id")
    private String id;
    private String title;
    private String description;
    private String priority;
    private boolean completed;
}