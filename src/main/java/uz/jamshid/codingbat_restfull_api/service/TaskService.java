package uz.jamshid.codingbat_restfull_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.codingbat_restfull_api.entity.Category;
import uz.jamshid.codingbat_restfull_api.entity.Task;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.TaskDto;
import uz.jamshid.codingbat_restfull_api.repository.CategoryRepository;
import uz.jamshid.codingbat_restfull_api.repository.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Adding new task (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param taskDto TaskDto
     * @return ApiResponse
     */
    public ApiResponse add(TaskDto taskDto) {
        boolean existsByName = taskRepository.existsByName(taskDto.getName());
        if (existsByName)
            return new ApiResponse("Task already exists", false);

        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category not found", false);
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setSolution(taskDto.getSolution());
        task.setCategory(optionalCategory.get());
        taskRepository.save(task);
        return new ApiResponse("Task added", true);
    }

    /**
     * Getting all tasks
     *
     * @param page int
     * @return ApiResponse
     */
    public ApiResponse get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ApiResponse("All tasks sent", true, taskRepository.findAll(pageable));
    }

    /**
     * Getting task by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse getById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return new ApiResponse("Task by id sent", true, optionalTask.orElse(null));
    }

    /**
     * Deleting task by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("TAsk not found", false);
        }
    }

    /**
     * Editing task by id
     *
     * @param id      Integer
     * @param taskDto TaskDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, TaskDto taskDto) {
        boolean existsByNameAndIdNot = taskRepository.existsByNameAndIdNot(taskDto.getName(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Task already exists", false);

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found", false);
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category not found", false);
        Task task = optionalTask.get();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setSolution(taskDto.getSolution());
        task.setCategory(optionalCategory.get());
        taskRepository.save(task);
        return new ApiResponse("Task edited", true);
    }
}
