package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.User;
import co.com.bancolombia.usecase.task.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerUser {
    private final UserUseCase userUserCase;
    public Mono<ServerResponse> registerUser(ServerRequest serverRequest){
        Mono<User> incomingObject = serverRequest.bodyToMono(User.class);
        return incomingObject.flatMap(object->ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUserCase.register(object), Object.class));
    }
}
