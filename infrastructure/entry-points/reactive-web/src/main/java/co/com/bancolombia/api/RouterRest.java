package co.com.bancolombia.api;

import co.com.bancolombia.api.config.TaskPath;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final TaskPath taskPath;
    private final Handler taskHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(taskPath.getTasks()), taskHandler::listenSaveTask)
                .andRoute(PUT(taskPath.getTasks()), taskHandler::listenUpdateTask)
                .andRoute(DELETE(taskPath.getTasksById()), taskHandler::listenDeleteTask)
                .andRoute(GET(taskPath.getTasks()), taskHandler::listenGetAllTasks)
                .andRoute(GET(taskPath.getTasksById()), taskHandler::listenGetTaskById);
    }
}
