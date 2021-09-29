package uz.jamshid.codingbat_restfull_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.UserDto;
import uz.jamshid.codingbat_restfull_api.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * Adding new User (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param userDto UserDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Getting all users
     *
     * @param page int
     * @return ApiResponse
     */
    @GetMapping
    public ResponseEntity<ApiResponse> get(@RequestParam int page) {
        return ResponseEntity.ok(userService.get(page));
    }

    /**
     * Getting user by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    /**
     * Deleting user by id (HttpStatus.NO_CONTENT - 204) (HttpStatus.CONFLICT - 409)
     *
     * @param id Integr
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing user by id
     *
     * @param id      Integer
     * @param userDto UserDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.edit(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
