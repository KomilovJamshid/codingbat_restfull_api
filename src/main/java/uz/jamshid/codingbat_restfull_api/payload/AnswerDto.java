package uz.jamshid.codingbat_restfull_api.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerDto {
    @NotNull(message = "Answer text field should not be empty")
    private String text;

    @NotNull(message = "Answer type should not be empty")
    private boolean isCorrect;

    private Integer userId;

    private Integer taskId;
}
