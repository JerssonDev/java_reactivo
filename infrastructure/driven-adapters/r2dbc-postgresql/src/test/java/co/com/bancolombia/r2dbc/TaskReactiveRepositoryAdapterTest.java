package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Priority;
import co.com.bancolombia.model.Task;
import co.com.bancolombia.r2dbc.entity.TaskEntity;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    TaskRepositoryAdapter repositoryAdapter;

    @Mock
    TaskReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private final UUID taskId = UUID.fromString("17c13bcc-5d6d-4670-820b-7df2a0a880a4");

    private final TaskEntity taskEntity = TaskEntity.builder()
            .id(taskId)
            .title("Tarea 1")
            .description("Descripción")
            .priority("LOW")
            .completed(false)
            .build();

    private final Task task = Task.builder()
            .id(taskId)
            .title("Tarea 1")
            .description("Descripción")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    @Test
    void shouldFindTaskById() {

        when(mapper.map(taskEntity, Task.class)).thenReturn(task);

        when(repository.findById(taskId)).thenReturn(Mono.just(taskEntity));

        Mono<Task> result = repositoryAdapter.findById(taskId);

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getId().equals(taskId) && t.getTitle().equals("Tarea 1"))
                .verifyComplete();
    }

    @Test
    void shouldFindAllTask() {
        when(mapper.map(taskEntity, Task.class)).thenReturn(task);
        when(repository.findAll()).thenReturn(Flux.just(taskEntity));

        Flux<Task> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNext(task)
                .verifyComplete();
    }

    @Test
    void shouldSaveTask() {
        when(mapper.map(taskEntity, Task.class)).thenReturn(task);
        when(mapper.map(task, TaskEntity.class)).thenReturn(taskEntity);
        when(repository.save(taskEntity)).thenReturn(Mono.just(taskEntity));

        Mono<Task> result = repositoryAdapter.save(task);

        StepVerifier.create(result)
                .expectNext(task)
                .verifyComplete();
    }

}
