package co.com.bancolombia.api;

import co.com.bancolombia.api.config.TaskPath;
import co.com.bancolombia.api.openapi.TaskOpenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final TaskPath taskPath;
    private final Handler taskHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route()
                .POST(taskPath.getTasks(), taskHandler::listenSaveTask, TaskOpenApi::saveTask)
                .PUT(taskPath.getTasks(), taskHandler::listenUpdateTask, TaskOpenApi::updateTask)
                .DELETE(taskPath.getTasksById(), taskHandler::listenDeleteTask, TaskOpenApi::deleteTask)
                .GET(taskPath.getTasks(), taskHandler::listenGetAllTasks, TaskOpenApi::getAllTasks)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> undocumentedRouterFunction() {
        return RouterFunctions
                .route()
                .GET(taskPath.getTasksById(), taskHandler::listenGetTaskById)
                .build();
    }
}
