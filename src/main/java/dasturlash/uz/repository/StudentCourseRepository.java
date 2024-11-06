package dasturlash.uz.repository;

import dasturlash.uz.entity.StudentCourseEntity;
import dasturlash.uz.mapper.StudentDetailMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseRepository extends CrudRepository<StudentCourseEntity,Integer> ,
        PagingAndSortingRepository<StudentCourseEntity,Integer> {

    List<StudentCourseEntity> findByStudentIdAndCreatedDateBetween(Integer id,
                                                                   LocalDateTime from,
                                                                   LocalDateTime to);

    List<StudentCourseEntity> findByStudentIdOrderByCreatedDateDesc(Integer studentId);

    List<StudentCourseEntity> findByStudentIdAndCourseIdOrderByCreatedDateDesc(Integer studentId,
                                                                                 Integer courseId);

    StudentCourseEntity findFirstByStudentIdOrderByCreatedDateDesc(Integer studentId);

    List<StudentCourseEntity> findTop3ByStudentIdOrderByMarkDesc(Integer studentId);

    StudentCourseEntity findTopByStudentIdOrderByCreatedDateAsc(Integer studentId);

    StudentCourseEntity findTopByStudentIdAndCourseIdOrderByCreatedDateAsc(Integer studentId,
                                                                            Integer courseId);

    List<StudentCourseEntity> findTopByStudentIdAndCourseIdOrderByMarkDesc(Integer studentId,
                                                                           Integer courseId);

    long countByStudentIdAndMarkGreaterThan(Integer studentId, Integer mark);

    List<StudentCourseEntity> findTopByCourseIdOrderByMarkDesc(Integer courseId);

    long countByCourseId(Integer courseId);

    @Query("select AVG (mark) from StudentCourseEntity where studentId=?1 ")
    Double getAvgMarkByStudentId(Integer studentId);

    @Query("select AVG (mark) from StudentCourseEntity where studentId=?1 and courseId=?2")
    Double getAvgMarkByStudentIdAndCourseId(Integer studentId, Integer courseId);

    @Query("select count (mark) from StudentCourseEntity where studentId=?1 and mark>?2")
    Long getMarkGreaterThanByStudentId(Integer studentId, Integer mark);

    @Query("select AVG (mark) from StudentCourseEntity where courseId=?1 ")
    Double getAvgMarkByCourseId(Integer studentId);

    @Query("select AVG (mark) from StudentCourseEntity where courseId=?1 ")
    Double getMarkGreaterThanCourseId(Integer courseId);

    @Query(value = "select sc.id as id, sc.mark as mark, sc.created_date as created_date," +
            " s.id as student_id, s.name as student_name, s.surname, " +
            " c.id as course_id, c.name as course_name" +
            " from student_course sc" +
            " inner join student s on s.id=sc.student_id" +
            " inner join course c on c.id=sc.course_id" +
            " where sc.id=?1", nativeQuery = true)
    StudentDetailMapper getStudentCourseDetailById(Integer id);

    @Query("select sc.id as id, sc.mark as mark, sc.createdDate as createdDate," +
            " s.id as studentId, s.name as studentName, s.surname as surname," +
            " c.id as courseId, c.name as courseName"+
            " from StudentCourseEntity as sc" +
            " inner join sc.student as s" +
            " inner join sc.course as c" +
            " where sc.id=?1")
    StudentDetailMapper getStudentCourseDetailByIdHQL(Integer id);

    Page<StudentCourseEntity> findByStudentId(Integer studentId, Pageable pageable);

    Page<StudentCourseEntity> findByCourseId(Integer courseId, Pageable pageable);
}
