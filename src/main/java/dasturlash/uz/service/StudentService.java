package dasturlash.uz.service;

import dasturlash.uz.dto.PageResponse;
import dasturlash.uz.dto.StudentDTO;
import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.dto.StudentShortInfoDTO;
import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.enums.Gender;
import dasturlash.uz.mapper.StudentInfoMapper;
import dasturlash.uz.repository.StudentCustomRepository;
import dasturlash.uz.repository.StudentRepository;
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
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCustomRepository studentCustomRepository;


    public StudentDTO create(StudentDTO dto) {
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLevel(dto.getLevel());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setCreatedDate(LocalDateTime.now());

        studentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        StudentEntity entity = optional.get();
        return toDTO(entity);
    }

    public StudentDTO update(Integer id, StudentDTO dto) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        StudentEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLevel(dto.getLevel());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        studentRepository.save(entity);

        return dto;
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getByName(String name) {
//        Iterable<StudentEntity> iterable = studentRepository.findByName(name);
//        List<StudentEntity> iterable =studentRepository.findAllByName(name);
        List<StudentEntity> iterable = studentRepository.findAllByNameNative(name);
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentDTO> getByGender(Gender gender) {
        Iterable<StudentEntity> iterable = studentRepository.findByGender(gender);
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentDTO> getByGivenDate(LocalDate givenDate) {
        // 2024-10-26
        // select * from student where created_date between '2024-10-26 00:00:00.000000' and '2024-10-26 23:59:59.999999';

        LocalDateTime from = LocalDateTime.of(givenDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(givenDate, LocalTime.MAX);

        Iterable<StudentEntity> iterable = studentRepository.findByCreatedDateBetween(from, to);
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentDTO> getByGivenDates(LocalDate fromDate, LocalDate toDate) {
        //  2024-01-20 2024-02-02
        //         // select * from student where created_date between '2024-01-20 00:00:00.000000' and '2024-02-02 23:59:59.999999';

        LocalDateTime from = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(toDate, LocalTime.MAX);

        Iterable<StudentEntity> iterable = studentRepository.findByCreatedDateBetween(from, to);
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentDTO> getAllAgeGreaterThan18() {
        List<StudentEntity> entityList = studentRepository.getAllAgeGreaterThan18();
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity entity : entityList) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public Boolean updateNameAndSurname(Integer id, StudentDTO dto) {
        int effectedRows = studentRepository.updateNameAndSurname(dto.getName(), dto.getSurname(), id);
        return effectedRows != 0;
    }

    public List<StudentDTO> studentIdAndNameList() {
        List<Object[]> entityList = studentRepository.getIdAndNameList();
        List<StudentDTO> dtoList = new LinkedList<>();

        for (Object[] obj : entityList) {
            StudentDTO dto = new StudentDTO();
            dto.setId((Integer) obj[0]);
            dto.setName((String) obj[1]);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<StudentDTO> studentIdAndNameListUsingConstructor() {
        List<StudentEntity> entityList = studentRepository.getIdAndNameListUsingConstructor();
        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentEntity entity : entityList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<StudentDTO> studentUsingNotEntityConstructor() {
        List<StudentShortInfoDTO> dtoList = studentRepository.getStudentUsingNotEntityConstructor();
        List<StudentDTO> list = new LinkedList<>();

        for (StudentShortInfoDTO element : dtoList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(element.getId());
            dto.setName(element.getName());
            list.add(dto);
        }
        return list;
    }

    public List<StudentDTO> studentInfoList() {
        List<StudentInfoMapper> mapperList = studentRepository.getStudentInfoList();
        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentInfoMapper mapper : mapperList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setSurname(mapper.getSurname());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public StudentDTO toDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLevel(entity.getLevel());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    // ========================= PAGINATION ======================

    public PageImpl<StudentDTO> pagination(int page, int size) {
//        Sort sort=Sort.by("createdDate").ascending();
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentEntity> pageObj = studentRepository.findAll(pageable);

        List<StudentEntity> entityList = pageObj.getContent();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }

        long total = pageObj.getTotalElements();
        return new PageImpl<StudentDTO>(dtoList, pageable, total);
    }

    public PageImpl<StudentDTO> getByNameWithPagination(String name, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");

        Pageable pageRequest = PageRequest.of(page, size, sort);
        Page<StudentEntity> pageObj = studentRepository.findByName(name, pageRequest);

        List<StudentEntity> entityList = pageObj.getContent();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }

        long total = pageObj.getTotalElements();
        return new PageImpl<StudentDTO>(dtoList, pageRequest, total);
    }

    public PageImpl<StudentDTO> paginationWithQuery(String name, int age, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");

        Pageable pageRequest = PageRequest.of(page, size, sort);
        Page<StudentEntity> pageObj = studentRepository.findAllNameAndAge(name, age, pageRequest);

        long total = pageObj.getTotalElements();
        List<StudentEntity> entityList = pageObj.getContent();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<StudentDTO>(dtoList, pageRequest, total);
    }

    public PageImpl<StudentDTO> paginationWithLevel(int level, int page, int size) {
        Sort sort = Sort.by("createdDate").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentEntity> result = studentRepository.findByLevel(level, pageable);

        long total = result.getTotalElements();
        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : result) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<StudentDTO>(dtoList, pageable, total);
    }

    // ========================= PAGINATION FILTER ======================


    public PageImpl<StudentDTO> filter(StudentFilterDTO filterDTO, int page, int size) {
        PageResponse<StudentEntity> result = studentCustomRepository.filter(filterDTO, page, size);

        List<StudentDTO> dtoList=new LinkedList<>();
        for (StudentEntity entity: result.getContent()){
           dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,PageRequest.of(page,size),result.getTotalElements());
    }

}
