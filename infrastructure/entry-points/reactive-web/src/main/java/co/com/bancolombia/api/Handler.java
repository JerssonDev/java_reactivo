package co.com.bancolombia.api;

import co.com.bancolombia.model.Task;
import co.com.bancolombia.usecase.task.TaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final TaskUseCase taskUseCase;

    public Mono<ServerResponse> listenSaveTask(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Task.class)
                .flatMap(taskUseCase::saveTask)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask));
    }

    public Mono<ServerResponse> listenUpdateTask(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Task.class)
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
                .body(taskUseCase.getAllTasks(), Task.class);
    }

    public Mono<ServerResponse> listenGetTaskById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return taskUseCase.getTaskById(id)
                .flatMap(task -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(task))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listenDeleteTask(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return taskUseCase.deleteTask(id)
                .then(ServerResponse.noContent().build());
    }
}
