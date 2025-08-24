package co.com.bancolombia.usecase.task;
import co.com.bancolombia.model.User;
import co.com.bancolombia.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository repository;

    public Mono<User> register(User user) {
        return repository.register(user);
    }
}
