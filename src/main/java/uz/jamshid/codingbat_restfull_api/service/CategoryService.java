package uz.jamshid.codingbat_restfull_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.codingbat_restfull_api.entity.Category;
import uz.jamshid.codingbat_restfull_api.entity.Language;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.CategoryDto;
import uz.jamshid.codingbat_restfull_api.repository.CategoryRepository;
import uz.jamshid.codingbat_restfull_api.repository.LanguageRepository;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;

    /**
     * Adding new category (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param categoryDto CategoryDto
     * @return ApiResponse
     */
    public ApiResponse add(CategoryDto categoryDto) {
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName)
            return new ApiResponse("Category already exists", false);

        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found", false);
        Category category = new Category();
        category.setName(category.getName());
        category.setLanguage(optionalLanguage.get());
        categoryRepository.save(category);
        return new ApiResponse("Category added", true);
    }

    /**
     * Getting all categories
     *
     * @param page int
     * @return ApiResponse
     */
    public ApiResponse get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ApiResponse("Categories sent", true, categoryRepository.findAll(pageable));
    }

    /**
     * Getting category by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return new ApiResponse("Category by id sent", true, optionalCategory.orElse(null));
    }

    /**
     * Deleting category by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Category deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("Category not found", false);
        }
    }

    /**
     * Editing category by id (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param id          Integer
     * @param categoryDto CategoryDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, CategoryDto categoryDto) {
        boolean existsByNameAndIdNot = categoryRepository.existsByNameAndIdNot(categoryDto.getName(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Category already exists", false);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category not found", false);
        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found", false);
        Category category = optionalCategory.get();
        category.setName(category.getName());
        category.setLanguage(optionalLanguage.get());
        categoryRepository.save(category);
        return new ApiResponse("Category edited", true);
    }
}
