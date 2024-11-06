package dasturlash.uz.service;

import dasturlash.uz.dto.CourseDTO;
import dasturlash.uz.dto.StudentCourseDTO;
import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentCourseEntity;
import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.mapper.StudentDetailMapper;
import dasturlash.uz.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    public StudentCourseDTO create(StudentCourseDTO dto) {
        StudentCourseEntity entity = new StudentCourseEntity();
        entity.setStudentId(dto.getStudentId());
        entity.setCourseId(dto.getCourseId());
        entity.setMark(dto.getMark());
        entity.setCreatedDate(LocalDateTime.now());

        studentCourseRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public StudentCourseDTO update(Integer id, StudentCourseDTO dto) {
        StudentCourseEntity entity = get(id);
        entity.setCourseId(dto.getCourseId());
        entity.setStudentId(dto.getStudentId());
        entity.setMark(dto.getMark());

        studentCourseRepository.save(entity);

        return dto;
    }

    public StudentCourseDTO getById(Integer id) {
        return toDTO(get(id));
    }

    public void deleteById(Integer id) {
        studentCourseRepository.deleteById(id);
    }

    public List<StudentCourseDTO> getAll() {
        Iterable<StudentCourseEntity> iterable = studentCourseRepository.findAll();
        List<StudentCourseDTO> list = new LinkedList<>();
        iterable.forEach(e -> list.add(toDTO(e)));
        return list;
    }

    public StudentCourseDTO getDetailById(Integer id) {
        StudentCourseEntity entity = get(id);

        StudentCourseDTO dto = new StudentCourseDTO();
        dto.setId(entity.getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());

        StudentDTO student = studentService.getById(entity.getStudentId());
        dto.setStudent(student);

        CourseDTO course = courseService.getById(entity.getCourseId());
        dto.setCourse(course);

        return dto;
    }

    public List<StudentCourseDTO> getStudentCourseMarkByGivenDate(Integer id, LocalDate localDate) {
        LocalDateTime from = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(localDate, LocalTime.MAX);
        List<StudentCourseEntity> list = studentCourseRepository.findByStudentIdAndCreatedDateBetween(id, from, to);

        List<StudentCourseDTO> dtoList = new LinkedList<>();
        list.forEach(e -> dtoList.add(toDTO(e)));
        return dtoList;
    }

    public List<StudentCourseDTO> getStudentCourseMarkBetweenDate(Integer id,
                                                                  LocalDate fromDate,
                                                                  LocalDate toDate) {
        LocalDateTime from = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(toDate, LocalTime.MAX);
        List<StudentCourseEntity> list = studentCourseRepository.findByStudentIdAndCreatedDateBetween(id, from, to);

        List<StudentCourseDTO> dtoList = new LinkedList<>();
        list.forEach(e -> dtoList.add(toDTO(e)));
        return dtoList;
    }

    public List<StudentCourseDTO> getByStudentId(Integer studentId) {
        List<StudentCourseEntity> list = studentCourseRepository.findByStudentIdOrderByCreatedDateDesc(studentId);

        List<StudentCourseDTO> dtoList = new LinkedList<>();
        list.forEach(e -> dtoList.add(toDTO(e)));
        return dtoList;
    }

    public List<StudentCourseDTO> getByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        List<StudentCourseEntity> list = studentCourseRepository.
                findByStudentIdAndCourseIdOrderByCreatedDateDesc(studentId, courseId);

        List<StudentCourseDTO> dtoList = new LinkedList<>();
        list.forEach(e -> dtoList.add(toDTO(e)));
        return dtoList;
    }

    public StudentCourseDTO getStudentLastMark(Integer studentId) {
        StudentCourseEntity entity = studentCourseRepository.findFirstByStudentIdOrderByCreatedDateDesc(studentId);
        if (entity == null) {
            return null;
        }

        StudentCourseDTO dto = toDTO(entity);

        CourseDTO course = courseService.getById(entity.getCourseId());
        dto.setCourse(course);
        return dto;
    }

    public StudentCourseDTO getStudentFirstMark(Integer studentId) {
        StudentCourseEntity entity = studentCourseRepository.findTopByStudentIdOrderByCreatedDateAsc(studentId);
        if (entity == null) {
            return null;
        }
        return toDTO(entity);
    }

    public StudentCourseDTO getStudentFirstMarkByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        StudentCourseEntity entity = studentCourseRepository.
                findTopByStudentIdAndCourseIdOrderByCreatedDateAsc(studentId, courseId);
        if (entity == null) {
            return null;
        }
        return toDTO(entity);
    }

    public long countMarkInCourse(Integer courseId) {
        return studentCourseRepository.countByCourseId(courseId);
    }

    public List<StudentCourseDTO> getStudentHighestMark(Integer studentId) {
        List<StudentCourseEntity> list = studentCourseRepository.findTop3ByStudentIdOrderByMarkDesc(studentId);

        List<StudentCourseDTO> dtoList = new LinkedList<>();
        list.forEach(e -> dtoList.add(toDTO(e)));
        return dtoList;
    }

    public StudentCourseDTO getStudentCourseByIdWithDetail(Integer id) {
        StudentDetailMapper mapper = studentCourseRepository.getStudentCourseDetailByIdHQL(id);

        StudentCourseDTO dto = new StudentCourseDTO();
        dto.setId(mapper.getId());
        dto.setMark(mapper.getMark());
        dto.setCreatedDate(mapper.getCreatedDate());

        StudentDTO student=new StudentDTO();
        student.setId(mapper.getStudentId());
        student.setName(mapper.getStudentName());
        student.setSurname(mapper.getSurname());

        CourseDTO course=new CourseDTO();
        course.setId(mapper.getCourseId());
        course.setName(mapper.getCourseName());

        dto.setStudent(student);
        dto.setCourse(course);
        return dto;
    }


    public StudentCourseEntity get(Integer id) {
        return studentCourseRepository.findById(id).orElseThrow(() -> {
            try {
                throw new IllegalAccessException("StudentCourse not found");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public StudentCourseDTO toDTO(StudentCourseEntity entity) {
        StudentCourseDTO dto = new StudentCourseDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudentId());
        dto.setCourseId(entity.getCourseId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
