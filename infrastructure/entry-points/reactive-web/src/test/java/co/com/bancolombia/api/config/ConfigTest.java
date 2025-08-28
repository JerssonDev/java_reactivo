package co.com.bancolombia.api.config;

import co.com.bancolombia.api.Handler;
import co.com.bancolombia.api.RouterRest;
import co.com.bancolombia.model.Priority;
import co.com.bancolombia.model.Task;
import co.com.bancolombia.usecase.task.TaskUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class, TaskPath.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private TaskUseCase taskUseCase;

    private final UUID taskIdOne = UUID.fromString("17c13bcc-5d6d-4670-820b-7df2a0a880a4");
    private final UUID taskIdTwo = UUID.fromString("a0277c60-bec6-4f62-8ee2-4e60a4f9c2ce");

    private final Task taskOne = Task.builder()
            .id(taskIdOne)
            .title("Task 1")
            .description("Description")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    private final Task taskTwo = Task.builder()
            .id(taskIdTwo)
            .title("Task 2")
            .description("Description")
            .priority(Priority.LOW)
            .completed(false)
            .build();

    @BeforeEach
    void setUp() {
        when(taskUseCase.getAllTasks()).thenReturn(Flux.just(taskOne, taskTwo));
    }

    @Test
    void corsConfigurationShouldAllowOrigins() {
        webTestClient.get()
                .uri("/api/v1/tasks")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}