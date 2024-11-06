package dasturlash.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "student_course")
public class StudentCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id")
    private Integer studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", updatable = false, insertable = false)
    private StudentEntity student;

    @Column(name = "course_id")
    private Integer courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", updatable = false, insertable = false)
    private CourseEntity course;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
