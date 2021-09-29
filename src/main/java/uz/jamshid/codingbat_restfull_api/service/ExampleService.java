package uz.jamshid.codingbat_restfull_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.codingbat_restfull_api.entity.Example;
import uz.jamshid.codingbat_restfull_api.entity.Task;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.ExampleDto;
import uz.jamshid.codingbat_restfull_api.repository.ExampleRepository;
import uz.jamshid.codingbat_restfull_api.repository.TaskRepository;

import java.util.Optional;

@Service
public class ExampleService {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;

    /**
     * Adding new example (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param exampleDto ExampleDto
     * @return ApiResponse
     */
    public ApiResponse add(ExampleDto exampleDto) {
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found", false);
        Example example = new Example();
        example.setText(exampleDto.getText());
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("Example added", true);
    }

    /**
     * Getting all examples
     *
     * @param page int
     * @return ApiResponse
     */
    public ApiResponse get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ApiResponse("All examples sent", true, exampleRepository.findAll(pageable));
    }

    /**
     * Getting example by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse getById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return new ApiResponse("Example by id sent", true, optionalTask.orElse(null));
    }

    /**
     * Deleting example by id (HttpStatus.NO_CONTENT - 204) (HttpStatus.CONFLICT - 409)
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            exampleRepository.deleteById(id);
            return new ApiResponse("Example deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("Example not found", false);
        }
    }

    /**
     * Updating example by id (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param id         Integer
     * @param exampleDto ExampleDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, ExampleDto exampleDto) {
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new ApiResponse("Example not found", false);
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found", false);
        Example example = optionalExample.get();
        example.setText(exampleDto.getText());
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("Example edited", true);
    }
}
