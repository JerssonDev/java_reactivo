package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.Priority;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

    private final TaskEntity taskEntity = TaskEntity.builder()
            .id("1")
            .title("Tarea 1")
            .description("Descripción")
            .priority("LOW")
            .completed(false)
            .build();

    private final Task task = Task.builder()
            .id("1")
            .title("Tarea 1")
            .description("Descripción")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    private final Task otherTask = Task.builder()
            .id("2")
            .title("Tarea 12")
            .description("Descripción 2")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    @Test
    void shouldFindTaskById() {

        when(mapper.map(taskEntity, Task.class)).thenReturn(task);

        when(repository.findById("1")).thenReturn(Mono.just(taskEntity));

        Mono<Task> result = repositoryAdapter.findById("1");

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getId().equals("1") && t.getTitle().equals("Tarea 1"))
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
