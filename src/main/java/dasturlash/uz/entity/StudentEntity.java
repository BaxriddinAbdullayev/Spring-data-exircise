package dasturlash.uz.entity;

import dasturlash.uz.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "level")
    private Integer level;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "visible")
    private Boolean visible=Boolean.TRUE;

    public StudentEntity(){

    }

    public StudentEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
