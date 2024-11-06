package dasturlash.uz.service;

import dasturlash.uz.dto.CourseDTO;
import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.CoursePaginationDTO;
import dasturlash.uz.dto.PageResponse;
import dasturlash.uz.entity.CourseEntity;
import dasturlash.uz.repository.CourseCustomRepository;
import dasturlash.uz.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCustomRepository courseCustomRepository;

    public CourseDTO create(CourseDTO dto) {
        CourseEntity entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreatedDate(LocalDateTime.now());

        courseRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);

        if (optional.isEmpty()) {
            return null;
        }
        CourseEntity entity = optional.get();
        return toDTO(entity);
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.findAll();
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public CourseDTO update(Integer id, CourseDTO dto) {
        Optional<CourseEntity> optional = courseRepository.findById(id);

        if (optional.isEmpty()) {
            return null;
        }
        CourseEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        courseRepository.save(entity);

        return dto;
    }

    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }

    public List<CourseDTO> getByName(String name) {
        Iterable<CourseEntity> iterable = courseRepository.findByName(name);
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<CourseDTO> getByPrice(Double price) {
        Iterable<CourseEntity> iterable = courseRepository.findByPrice(price);
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<CourseDTO> getByDuration(Integer duration) {
        Iterable<CourseEntity> iterable = courseRepository.findByDuration(duration);
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<CourseDTO> getByPrices(Double fromPrice, Double toPrice) {
        Iterable<CourseEntity> iterable = courseRepository.findByPriceBetween(fromPrice, toPrice);
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<CourseDTO> getByCreatedDateBetween(LocalDate fromDate, LocalDate toDate) {

        LocalDateTime from = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(toDate, LocalTime.MAX);

        Iterable<CourseEntity> iterable = courseRepository.findByCreatedDateBetween(from, to);
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public PageImpl<CourseDTO> getByPricesBetween(CoursePaginationDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").ascending());
        Page<CourseEntity> result = courseRepository.findByPriceBetween(dto.getFromPrice(), dto.getToPrice(), pageable);

        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : result) {
            list.add(toDTO(entity));
        }
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    public CourseDTO toDTO(CourseEntity entity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PageImpl<CourseDTO> filter(CourseFilterDTO filter, int page, int size) {
        PageResponse<CourseEntity> result = courseCustomRepository.filter(filter, page, size);
        List<CourseDTO> list = new LinkedList<>();

        for (CourseEntity entity : result.getContent()) {
            list.add(toDTO(entity));
        }
        return new PageImpl<>(list,PageRequest.of(page,size),result.getTotalElements());
    }
}
