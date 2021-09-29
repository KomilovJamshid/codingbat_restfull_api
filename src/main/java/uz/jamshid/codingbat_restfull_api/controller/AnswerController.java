package uz.jamshid.codingbat_restfull_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.codingbat_restfull_api.payload.AnswerDto;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.service.AnswerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    /**
     * Adding new answer (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param answerDto AnswerDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody AnswerDto answerDto) {
        ApiResponse apiResponse = answerService.add(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Getting all answers
     *
     * @param page int
     * @return ApiResponse
     */
    @GetMapping()
    public ResponseEntity<ApiResponse> get(@RequestParam int page) {
        return ResponseEntity.ok(answerService.get(page));
    }

    /**
     * Getting answer id
     *
     * @param id Integer
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(answerService.getById(id));
    }

    /**
     * Deleting answer by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = answerService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Updating answer by id
     *
     * @param id        Integer
     * @param answerDto AnswerDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @Valid @RequestBody AnswerDto answerDto) {
        ApiResponse apiResponse = answerService.edit(id, answerDto);
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
