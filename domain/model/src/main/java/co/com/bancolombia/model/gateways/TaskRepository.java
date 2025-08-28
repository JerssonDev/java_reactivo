package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TaskRepository {

    Mono<Task> save(Task task);

    Flux<Task> findAll();

    Mono<Task> findById(UUID id);

    Mono<Void> deleteById(UUID id);
}
