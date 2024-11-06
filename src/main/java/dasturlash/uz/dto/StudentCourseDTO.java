package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentCourseDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Integer mark;
    private LocalDateTime createdDate;
    private StudentDTO student;
    private CourseDTO course;
}
