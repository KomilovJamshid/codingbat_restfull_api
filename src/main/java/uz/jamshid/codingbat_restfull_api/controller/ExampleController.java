package uz.jamshid.codingbat_restfull_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.ExampleDto;
import uz.jamshid.codingbat_restfull_api.service.ExampleService;

@RestController
@RequestMapping("/api/example")
public class ExampleController {
    @Autowired
    ExampleService exampleService;

    /**
     * Adding new example (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param exampleDto ExampleDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody ExampleDto exampleDto) {
        ApiResponse apiResponse = exampleService.add(exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Getting all examples
     *
     * @param page int
     * @return ApiResponse
     */
    @GetMapping
    public ResponseEntity<ApiResponse> get(@RequestParam int page) {
        return ResponseEntity.ok(exampleService.get(page));
    }

    /**
     * Getting example by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(exampleService.getById(id));
    }

    /**
     * Deleting example by id (HttpStatus.NO_CONTENT - 204) (HttpStatus.CONFLICT - 409)
     *
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = exampleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Updating example by id (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param id         Integer
     * @param exampleDto ExampleDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @RequestBody ExampleDto exampleDto) {
        ApiResponse apiResponse = exampleService.edit(id, exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }
}
