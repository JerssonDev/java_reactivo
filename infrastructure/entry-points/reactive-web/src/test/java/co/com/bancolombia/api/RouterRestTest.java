package co.com.bancolombia.api;

import co.com.bancolombia.model.Priority;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, HandlerTask.class})
@EnableConfigurationProperties(TaskPath.class)
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private TaskUseCase taskUseCase;

    private final String tasks = "/api/v1/tasks";
    private final String tasksById = "/api/v1/tasks";

    private final Task taskOne = Task.builder()
            .id("1")
            .title("Task 1")
            .description("Description")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    private final Task taskTwo = Task.builder()
            .id("2")
            .title("Task 2")
            .description("Description")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    @Autowired
    private TaskPath taskPath;

    @Test
    void shouldLoadTaskPathProperties() {
        assertEquals("/api/v1/tasks", taskPath.getTasks());
        assertEquals("/api/v1/tasks/{id}", taskPath.getTasksById());
    }

    @Test
    void shouldGetAllTasks() {

        when(taskUseCase.getAllTasks()).thenReturn(Flux.just(taskOne, taskTwo));

        webTestClient.get()
                .uri(tasks)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Task.class)
                .hasSize(2)
                .value(tasks -> {
                    Assertions.assertThat(tasks).isNotEmpty();
                    Assertions.assertThat(tasks.get(0).getId()).isEqualTo("1");
                });

    }

    @Test
    void shouldGetTaskById() {
        String id = "1";

        when(taskUseCase.getTaskById(id)).thenReturn(Mono.just(taskOne));

        webTestClient.get()
                .uri(tasks + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(response -> Assertions.assertThat(response.getId()).isEqualTo(id));
    }

    @Test
    void shouldPostSaveTask() {

        when(taskUseCase.saveTask(any(Task.class))).thenReturn(Mono.just(taskOne));

        webTestClient.post()
                .uri(tasks)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskOne)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(saved -> Assertions.assertThat(saved.getTitle()).isEqualTo(taskOne.getTitle()));
    }

    @Test
    void shouldPutUpdateTask() {
        Task task = Task.builder()
                .id("1")
                .title("Task 1")
                .description("Description")
                .priority(Priority.HIGH)
                .completed(true)
                .build();

        when(taskUseCase.updateTask(any(Task.class))).thenReturn(Mono.just(task));

        webTestClient.put()
                .uri(tasks)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(updated -> Assertions.assertThat(updated.isCompleted()).isTrue());
    }

    @Test
    void shouldDeleteTask() {
        String id = "1";
        when(taskUseCase.deleteTask(id)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(tasks + "/" + id)
                .exchange()
                .expectStatus().isNoContent();
    }
}
