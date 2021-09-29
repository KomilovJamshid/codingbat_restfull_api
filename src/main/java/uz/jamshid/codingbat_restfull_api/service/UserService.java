package uz.jamshid.codingbat_restfull_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.codingbat_restfull_api.entity.User;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.payload.UserDto;
import uz.jamshid.codingbat_restfull_api.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * Adding new User (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param userDto UserDto
     * @return ApiResponse
     */
    public ApiResponse add(UserDto userDto) {
        boolean existsByEmail = userRepository.existsByEmail(userDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Email already exists", false);
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("User added", true);
    }

    /**
     * Getting all users
     *
     * @param page int
     * @return ApiResponse
     */
    public ApiResponse get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ApiResponse("All users sent", true, userRepository.findAll(pageable));
    }

    /**
     * Getting user by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return new ApiResponse("User sent by id", true, optionalUser.orElse(null));
    }

    /**
     * Deleting user by id (HttpStatus.NO_CONTENT - 204) (HttpStatus.CONFLICT - 409)
     *
     * @param id Integr
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse("User deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("USer not found", false);
        }
    }

    /**
     * Editing user by id
     *
     * @param id      Integer
     * @param userDto UserDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, UserDto userDto) {
        boolean existsByEmailAndIdNot = userRepository.existsByEmailAndIdNot(userDto.getEmail(), id);
        if (existsByEmailAndIdNot)
            return new ApiResponse("Email already exists", false);
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found", false);
        User user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("User edited", true);
    }
}
