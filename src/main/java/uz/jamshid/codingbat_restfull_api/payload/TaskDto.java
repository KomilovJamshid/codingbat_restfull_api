package uz.jamshid.codingbat_restfull_api.payload;

import lombok.Data;

@Data
public class TaskDto {
    private String name;
    private String description;
    private String solution;
    private Integer categoryId;
}
