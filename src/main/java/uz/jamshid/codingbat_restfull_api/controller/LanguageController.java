package uz.jamshid.codingbat_restfull_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.codingbat_restfull_api.entity.Language;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.service.LanguageService;

@RestController
@RequestMapping("/api/language")
public class LanguageController {
    @Autowired
    LanguageService languageService;

    /**
     * Adding new language (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param language Language
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody Language language) {
        ApiResponse apiResponse = languageService.add(language);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Getting all languages
     *
     * @param page int
     * @return ApiResponse
     */
    @GetMapping
    public ResponseEntity<ApiResponse> get(@RequestParam int page) {
        return ResponseEntity.ok(languageService.get(page));
    }

    /**
     * Getting language by id
     *
     * @param id Integer
     * @return ApiREsponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

    /**
     * Deleting language by id (HttpStatus.NO_CONTENT - 204) (HttpStatus.CONFLICT - 409)
     *
     * @param id Integer
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = languageService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing language by id (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param id       Integer
     * @param language Language
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @RequestBody Language language) {
        ApiResponse apiResponse = languageService.edit(id, language);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }
}
