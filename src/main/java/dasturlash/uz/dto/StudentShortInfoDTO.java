package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentShortInfoDTO {
    private Integer id;
    private String name;
    private String surname;

    public StudentShortInfoDTO(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
