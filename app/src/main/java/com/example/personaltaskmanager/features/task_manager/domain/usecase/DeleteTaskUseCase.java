package com.example.personaltaskmanager.features.task_manager.domain.usecase;

import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.data.repository.TaskRepository;

/**
 * UseCase xoá Task — tách logic cho sạch kiến trúc.
 */
public class DeleteTaskUseCase {

    private final TaskRepository repository;

    public DeleteTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void execute(Task task) {
        repository.deleteTask(task);
    }
}
