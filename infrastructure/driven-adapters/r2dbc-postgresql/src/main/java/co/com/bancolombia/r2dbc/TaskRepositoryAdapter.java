package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Task;
import co.com.bancolombia.model.gateways.TaskRepository;
import co.com.bancolombia.r2dbc.entity.TaskEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class TaskRepositoryAdapter extends ReactiveAdapterOperations<
        Task,
        TaskEntity,
        UUID,
        TaskReactiveRepository
> implements TaskRepository {
    public TaskRepositoryAdapter(TaskReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, Task.class));
    }

    @Override
    public Mono<Task> save(Task task) {
        return super.save(task);
    }
    @Override
    public Flux<Task> findAll() {
        return super.findAll();
    }

    @Override
    public Mono<Task> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return repository.deleteById(id);
    }

}
