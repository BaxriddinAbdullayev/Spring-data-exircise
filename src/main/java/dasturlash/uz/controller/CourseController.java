package dasturlash.uz.controller;

import dasturlash.uz.dto.*;
import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.service.CourseService;
import dasturlash.uz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("")
    private ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto){
        return ResponseEntity.ok(courseService.create(dto));
    }

    @GetMapping("")
    private ResponseEntity<List<CourseDTO>> all(){
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<CourseDTO> byId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<CourseDTO> update(@PathVariable("id") Integer id,
                                             @RequestBody CourseDTO dto){
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<CourseDTO> delete(@PathVariable("id") Integer id){
        courseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-name")
    private ResponseEntity<List<CourseDTO>> byName(@RequestParam("name") String name){
        return ResponseEntity.ok(courseService.getByName(name));
    }

    @GetMapping("/by-price/{price}")
    private ResponseEntity<List<CourseDTO>> byPrice(@PathVariable("price") Double price){
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    @GetMapping("/by-duration")
    private ResponseEntity<List<CourseDTO>> byDuration(@RequestParam("duration") Integer duration){
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }

    @GetMapping("/between-price")
    private ResponseEntity<List<CourseDTO>> byPrices(@RequestParam("from_price") Double fromPrice,
                                                     @RequestParam("to_price") Double toPrice){
        return ResponseEntity.ok(courseService.getByPrices(fromPrice,toPrice));
    }

    @GetMapping("/between-date")
    private ResponseEntity<List<CourseDTO>> byCreatedDates(@RequestParam("from_date") LocalDate fromDate,
                                                     @RequestParam("to_date") LocalDate toDadte){
        return ResponseEntity.ok(courseService.getByCreatedDateBetween(fromDate,toDadte));
    }

    @PostMapping("/between-price")
    private ResponseEntity<Page<CourseDTO>> paginationByPriceBetween(@RequestBody CoursePaginationDTO dto,
                                                                     @RequestParam(value = "page",defaultValue = "1") int page,
                                                                     @RequestParam(value = "size",defaultValue = "10") int size){
        return ResponseEntity.ok(courseService.getByPricesBetween(dto, PageUtil.getCurrentPage(page),size));
    }

    @PostMapping("/filter")
    private ResponseEntity<PageImpl<CourseDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                        @RequestBody CourseFilterDTO filter) {
        PageImpl<CourseDTO> result = courseService.filter(filter, PageUtil.getCurrentPage(page), size);
        return ResponseEntity.ok(result);
    }
}
