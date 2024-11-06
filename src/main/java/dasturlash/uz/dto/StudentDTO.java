package dasturlash.uz.dto;

import dasturlash.uz.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentDTO {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private Integer level;
    private Gender gender;
    private LocalDateTime createdDate;
}
