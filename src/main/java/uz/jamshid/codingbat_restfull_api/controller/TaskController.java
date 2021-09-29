package uz.jamshid.codingbat_restfull_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.TaskDto;
import uz.jamshid.codingbat_restfull_api.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    /**
     * Adding new task (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param taskDto TaskDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.add(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Getting all tasks
     *
     * @param page int
     * @return ApiResponse
     */
    @GetMapping
    public ResponseEntity<ApiResponse> get(@RequestParam int page) {
        return ResponseEntity.ok(taskService.get(page));
    }

    /**
     * Getting task by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    /**
     * Deleting task by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = taskService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing task by id
     *
     * @param id      Integer
     * @param taskDto TaskDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.edit(id, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }
}
