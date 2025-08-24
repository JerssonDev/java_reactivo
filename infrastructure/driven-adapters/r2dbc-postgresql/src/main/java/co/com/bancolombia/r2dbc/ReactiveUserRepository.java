package co.com.bancolombia.r2dbc;
import co.com.bancolombia.model.User;
import co.com.bancolombia.r2dbc.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


import java.util.UUID;


public interface ReactiveUserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserEntity> findByEmail(String email);
    Mono<UserEntity> save(User user);
}