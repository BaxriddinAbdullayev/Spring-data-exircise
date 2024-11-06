package dasturlash.uz.mapper;

import java.time.LocalDateTime;

public interface StudentDetailMapper {
    Integer getId();

    Integer getMark();

    LocalDateTime getCreatedDate();

    Integer getStudentId();

    String getStudentName();

    String getSurname();

    Integer getCourseId();

    String getCourseName();
}
