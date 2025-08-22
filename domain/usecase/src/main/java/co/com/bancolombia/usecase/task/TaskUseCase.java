package co.com.bancolombia.usecase.task;

import co.com.bancolombia.model.Task;
import co.com.bancolombia.model.gateways.TaskRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TaskUseCase {

    private final TaskRepository taskRepository;

    public Mono<Task> saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Mono<Task> updateTask(Task task) {
        return taskRepository.save(task);
    }

    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Mono<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Mono<Void> deleteTask(String id) {
        return taskRepository.deleteById(id);
    }
}
