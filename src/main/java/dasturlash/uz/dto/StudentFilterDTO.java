package dasturlash.uz.dto;

import dasturlash.uz.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private Integer level;
    private Gender gender;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
