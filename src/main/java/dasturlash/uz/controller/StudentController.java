package dasturlash.uz.controller;

import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.enums.Gender;
import dasturlash.uz.service.StudentService;
import dasturlash.uz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("")
    private ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        StudentDTO resullt = studentService.create(dto);
        return ResponseEntity.ok(resullt);
    }

    @GetMapping("")
    private ResponseEntity<List<StudentDTO>> all() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<StudentDTO> byId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<StudentDTO> update(@PathVariable("id") Integer id,
                                              @RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<StudentDTO> delete(@PathVariable("id") Integer id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-name/{name}")
    private ResponseEntity<List<StudentDTO>> byName(@PathVariable("name") String name) {
        return ResponseEntity.ok(studentService.getByName(name));
    }

    @GetMapping("/by-gender/{gender}")
    private ResponseEntity<List<StudentDTO>> byGender(@PathVariable("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getByGender(gender));
    }

    @GetMapping("/given-date")
    private ResponseEntity<List<StudentDTO>> byGivenDate(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(studentService.getByGivenDate(date));
    }

    @GetMapping("/given-dates")
    private ResponseEntity<List<StudentDTO>> byGivenDates(@RequestParam("fromDate") LocalDate fromDate,
                                                          @RequestParam("toDate") LocalDate toDate) {
        return ResponseEntity.ok(studentService.getByGivenDates(fromDate, toDate));
    }

    @GetMapping("/age-18")
    private ResponseEntity<List<StudentDTO>> ageGreater18() {
        return ResponseEntity.ok(studentService.getAllAgeGreaterThan18());
    }

    @GetMapping("/test")
    private ResponseEntity<List<StudentDTO>> test() {
        return ResponseEntity.ok(studentService.studentInfoList());
    }


    // ========================= PAGINATION ======================

    @GetMapping("/pagination")
    private ResponseEntity<PageImpl<StudentDTO>> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.pagination(PageUtil.getCurrentPage(page), size));
    }

    @PostMapping("/pagination")
    private ResponseEntity<PageImpl<StudentDTO>> pagination(@RequestBody StudentDTO dto,
                                                            @RequestParam("page") int page,
                                                            @RequestParam("size") int size) {
        return ResponseEntity.ok(studentService.getByNameWithPagination(
                dto.getName(),
                PageUtil.getCurrentPage(page),
                size));
    }

    @PostMapping("/pagination/query")
    private ResponseEntity<PageImpl<StudentDTO>> paginationWithQuery(@RequestBody StudentDTO dto,
                                                                     @RequestParam("page") int page,
                                                                     @RequestParam("size") int size) {
        return ResponseEntity.ok(studentService.paginationWithQuery(
                dto.getName(),
                dto.getAge(),
                PageUtil.getCurrentPage(page),
                size));
    }

    @GetMapping("/pagination/{level}")
    private ResponseEntity<PageImpl<StudentDTO>> pagination(
            @PathVariable("level") Integer level,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.paginationWithLevel(level, PageUtil.getCurrentPage(page), size));
    }

    // ========================= PAGINATION FILTER ======================

    @PostMapping("/filter")
    private ResponseEntity<PageImpl<StudentDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                        @RequestBody StudentFilterDTO filter) {
        PageImpl<StudentDTO> result = studentService.filter(filter, PageUtil.getCurrentPage(page), size);
        return ResponseEntity.ok(result);
    }
}
