package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.SaveTaskDTO;
import co.com.bancolombia.api.dto.TaskDTO;
import co.com.bancolombia.api.dto.UpdateTaskDTO;
import co.com.bancolombia.model.Task;
import co.com.bancolombia.usecase.task.TaskUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Handler {

    private final TaskUseCase taskUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenSaveTask(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SaveTaskDTO.class)
                .map(task -> objectMapper.convertValue(task, Task.class))
                .flatMap(taskUseCase::saveTask)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask));
    }

    public Mono<ServerResponse> listenUpdateTask(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpdateTaskDTO.class)
                .map(task -> objectMapper.convertValue(task, Task.class))
                .flatMap(taskUseCase::updateTask)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask));
    }


    public Mono<ServerResponse> listenGetAllTasks(ServerRequest serverRequest) {
        return ServerResponse.ok()
                //.contentType(MediaType.APPLICATION_JSON)
                //.contentType(MediaType.APPLICATION_NDJSON)
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(taskUseCase.getAllTasks(), TaskDTO.class);
    }

    public Mono<ServerResponse> listenGetTaskById(ServerRequest serverRequest) {
        return Mono.fromCallable(() -> serverRequest.pathVariable("id"))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .map(UUID::fromString)
                .flatMap(taskUseCase::getTaskById)
                .flatMap(task -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(task))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listenDeleteTask(ServerRequest serverRequest) {

        return Mono.fromCallable(() -> serverRequest.pathVariable("id"))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .map(UUID::fromString)
                .flatMap(id -> taskUseCase.deleteTask(id)
                        .then(ServerResponse.noContent().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
