package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.TaskEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

// TODO: This file is just an example, you should delete or modify it
public interface TaskReactiveRepository extends ReactiveCrudRepository<TaskEntity, UUID>, ReactiveQueryByExampleExecutor<TaskEntity> {

}
