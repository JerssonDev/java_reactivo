package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository {

    Mono<Task> save(Task task);

    Flux<Task> findAll();

    Mono<Task> findById(String id);

    Mono<Void> deleteById(String id);
}
