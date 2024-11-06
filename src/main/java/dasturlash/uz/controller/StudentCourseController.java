package dasturlash.uz.controller;

import dasturlash.uz.dto.StudentCourseDTO;
import dasturlash.uz.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("student-course")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("")
    private ResponseEntity<StudentCourseDTO> create(@RequestBody StudentCourseDTO dto) {
        return ResponseEntity.ok(studentCourseService.create(dto));
    }

    @PutMapping("/{id}")
    private ResponseEntity<StudentCourseDTO> update(@PathVariable("id") Integer id,
                                                    @RequestBody StudentCourseDTO dto) {
        return ResponseEntity.ok(studentCourseService.update(id, dto));
    }

    @GetMapping("/{id}")
    private ResponseEntity<StudentCourseDTO> byId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseService.getById(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        studentCourseService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    private ResponseEntity<List<StudentCourseDTO>> all() {
        return ResponseEntity.ok(studentCourseService.getAll());
    }

    @GetMapping("/detail/{id}")
    private ResponseEntity<StudentCourseDTO> detailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseService.getDetailById(id));
    }

    @GetMapping("/given-date/{id}")
    private ResponseEntity<List<StudentCourseDTO>> studentCourseMarkByGivenDate(@PathVariable("id") Integer studentId,
                                                                                @RequestParam("date") LocalDate localDate) {
        return ResponseEntity.ok(studentCourseService.getStudentCourseMarkByGivenDate(studentId, localDate));
    }

    @GetMapping("/between-date/{studentId}")
    private ResponseEntity<List<StudentCourseDTO>> studentCourseMarkBetweenDate(@PathVariable("studentId") Integer studentId,
                                                                                @RequestParam("fromDate") LocalDate fromDate,
                                                                                @RequestParam("toDate") LocalDate toDate) {
        return ResponseEntity.ok(studentCourseService.getStudentCourseMarkBetweenDate(studentId, fromDate, toDate));
    }

    @GetMapping("/student/{studentId}")
    private ResponseEntity<List<StudentCourseDTO>> byStudentId(@PathVariable("studentId") Integer studentId) {
        return ResponseEntity.ok(studentCourseService.getByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    private ResponseEntity<List<StudentCourseDTO>> byStudentAndCourseId(@PathVariable("studentId") Integer studentId,
                                                                        @PathVariable("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseService.getByStudentIdAndCourseId(studentId,courseId));
    }

    @GetMapping("/student/{studentId}/last")
    private ResponseEntity<StudentCourseDTO> getLastMark(@PathVariable("studentId") Integer studentId) {
        return ResponseEntity.ok(studentCourseService.getStudentLastMark(studentId));
    }

    @GetMapping("/student/{studentId}/top3")
    private ResponseEntity<List<StudentCourseDTO>> getTop3Mark(@PathVariable("studentId") Integer studentId) {
        return ResponseEntity.ok(studentCourseService.getStudentHighestMark(studentId));
    }

    @GetMapping("/student/{studentId}/firstMark")
    private ResponseEntity<StudentCourseDTO> getFirstMark(@PathVariable("studentId") Integer studentId) {
        return ResponseEntity.ok(studentCourseService.getStudentFirstMark(studentId));
    }

    @GetMapping("/student/{studentId}/course/{courseId}/firstMark")
    private ResponseEntity<StudentCourseDTO> getFirstMarkByStudentIdAndCourseId(@PathVariable("studentId") Integer studentId,
                                                                                @PathVariable("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseService.getStudentFirstMarkByStudentIdAndCourseId(studentId,courseId));
    }

    @GetMapping("/course/{courseId}")
    private ResponseEntity<Long> getMarkInCourse(@PathVariable("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseService.countMarkInCourse(courseId));
    }

    @GetMapping("/by-id-detail/{id}")
    private ResponseEntity<StudentCourseDTO> getStudentCourseByIdWithDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseService.getStudentCourseByIdWithDetail(id));
    }
}
