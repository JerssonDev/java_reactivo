package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.api.handler.HandlerUser;
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
    private final UserPath userPath;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(HandlerUser handlerUser) {
        return route(POST(userPath.getUsers()),handlerUser::registerUser);

    }
}
