package uz.jamshid.codingbat_restfull_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.codingbat_restfull_api.entity.Language;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.repository.LanguageRepository;

import java.util.Optional;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    /**
     * Adding new language (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param language Language
     * @return ApiResponse
     */
    public ApiResponse add(Language language) {
        boolean existsByName = languageRepository.existsByName(language.getName());
        if (existsByName)
            return new ApiResponse("Language already exists", false);
        languageRepository.save(language);
        return new ApiResponse("Language added", true);
    }

    /**
     * Getting all languages
     *
     * @param page int
     * @return ApiResponse
     */
    public ApiResponse get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ApiResponse("All languages sent", true, languageRepository.findAll(pageable));
    }

    /**
     * Getting language by id
     *
     * @param id Integer
     * @return ApiREsponse
     */
    public ApiResponse getById(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return new ApiResponse("Language by id sent", true, optionalLanguage.orElse(null));
    }

    /**
     * Deleting language by id (HttpStatus.NO_CONTENT - 204) (HttpStatus.CONFLICT - 409)
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            languageRepository.deleteById(id);
            return new ApiResponse("Language deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("Language not found", false);
        }
    }

    /**
     * Editing language by id (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param id       Integer
     * @param language Language
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, Language language) {
        boolean existsByNameAndIdNot = languageRepository.existsByNameAndIdNot(language.getName(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Language already exists", false);

        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found", false);
        Language currentLanguage = optionalLanguage.get();
        currentLanguage.setName(language.getName());
        languageRepository.save(currentLanguage);
        return new ApiResponse("Language edited", true);
    }
}
