package co.com.bancolombia.r2dbc;
import co.com.bancolombia.model.User;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import org.reactivecommons.utils.ObjectMapper;
@RequiredArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryAdapter.class);
    private final ReactiveUserRepository repo;
    private final ObjectMapper mapper;

    @Override
    public Mono<User> register(User user) {
        return repo.save(mapper.map(user, UserEntity.class))
                .map(entity -> mapper.map(entity, User.class));
    }


    @Override
    public Mono<User> findByEmail(String email) {
        return repo.findByEmail(email).map(user-> mapper.map(user,User.class));
    }
}