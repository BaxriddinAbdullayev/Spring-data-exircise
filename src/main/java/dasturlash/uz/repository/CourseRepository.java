package dasturlash.uz.repository;

import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer> ,
        PagingAndSortingRepository<CourseEntity,Integer> {

    List<CourseEntity> findByName(String name);

    List<CourseEntity> findByPrice(Double price);

    List<CourseEntity> findByDuration(Integer duration);

    List<CourseEntity> findByPriceBetween(Double fromPrice, Double toPrice);

    List<CourseEntity> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<CourseEntity> findByPrice(Double price, Pageable pageable);

    Page<CourseEntity> findByPriceBetween(Double fromPrice, Double toPrice, Pageable pageable);
}
