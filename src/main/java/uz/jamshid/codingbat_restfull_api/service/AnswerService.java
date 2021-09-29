package uz.jamshid.codingbat_restfull_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.jamshid.codingbat_restfull_api.entity.Answer;
import uz.jamshid.codingbat_restfull_api.entity.Task;
import uz.jamshid.codingbat_restfull_api.entity.User;
import uz.jamshid.codingbat_restfull_api.payload.AnswerDto;
import uz.jamshid.codingbat_restfull_api.payload.ApiResponse;
import uz.jamshid.codingbat_restfull_api.repository.AnswerRepository;
import uz.jamshid.codingbat_restfull_api.repository.TaskRepository;
import uz.jamshid.codingbat_restfull_api.repository.UserRepository;

import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    /**
     * Adding new answer (HttpStatus.CREATED - 201) (HttpStatus.CONFLICT - 409)
     *
     * @param answerDto AnswerDto
     * @return ApiResponse
     */
    public ApiResponse add(AnswerDto answerDto) {
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found", false);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found", false);
        Answer answer = new Answer();
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
        answer.setUser(optionalUser.get());
        answer.setTask(optionalTask.get());
        answerRepository.save(answer);
        return new ApiResponse("Answer added", true);
    }

    /**
     * Getting all answers
     *
     * @param page int
     * @return ApiResponse
     */
    public ApiResponse get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ApiResponse("All answers sent", true, answerRepository.findAll(pageable));
    }

    /**
     * Getting answer id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return new ApiResponse("Answer by id sent", false, optionalUser.orElse(null));
    }

    /**
     * Deleting answer by id
     *
     * @param id Integer
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id) {
        try {
            answerRepository.deleteById(id);
            return new ApiResponse("Answer deleted", true);
        } catch (Exception exception) {
            return new ApiResponse("Answer not found", false);
        }
    }

    /**
     * Updating answer by id
     *
     * @param id        Integer
     * @param answerDto AnswerDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("Answer not found", false);
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found", false);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found", false);
        Answer answer = optionalAnswer.get();
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
        answer.setUser(optionalUser.get());
        answer.setTask(optionalTask.get());
        answerRepository.save(answer);
        return new ApiResponse("Answer edited", true);
    }
}
