package dasturlash.uz.repository;

import dasturlash.uz.dto.StudentShortInfoDTO;
import dasturlash.uz.entity.StudentEntity;
import dasturlash.uz.enums.Gender;
import dasturlash.uz.mapper.StudentInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>,
        PagingAndSortingRepository<StudentEntity, Integer> {

    List<StudentEntity> findByName(String name);

    List<StudentEntity> findBySurname(String surname);

    List<StudentEntity> findByLevel(Integer level);

    List<StudentEntity> findByAge(Integer age);

    List<StudentEntity> findByGender(Gender gender);

    List<StudentEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("from StudentEntity ")
        // select * from student
    List<StudentEntity> getAll();

    @Query("from StudentEntity where id=?1")
    StudentEntity getById(Integer id);

    @Modifying
    @Transactional
    @Query("update StudentEntity set name=:name , surname=:surname, level=:level,age=:age, gender=:gender where id=:id")
    int update(@Param("name") String name,
               @Param("surname") String surname,
               @Param("level") Integer level,
               @Param("age") Integer age,
               @Param("gender") Gender gender,
               @Param("id") Integer id);

    @Query("from StudentEntity where createdDate between ?1 and ?2")
    List<StudentEntity> getByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("from StudentEntity where age>18")
        // select * from student where age>18
    List<StudentEntity> getAllAgeGreaterThan18();

    @Query("from StudentEntity where name=:nameP")
        // select * from student where name=?
    List<StudentEntity> findAllByName(@Param("nameP") String name);

    @Query("from StudentEntity where surname=:surnameP")
    List<StudentEntity> findBySurnameByQuery(@Param("surnameP") String surname);

    @Query("from StudentEntity where level=:level")
    List<StudentEntity> findByLevelByQuery(@Param("level") String level);

    @Query("from StudentEntity where gender=:gender")
    List<StudentEntity> findByGenderByQuery(@Param("gender") Gender gender);

    @Query("from StudentEntity where name=: name and surname=: surname and age=: age")
    List<StudentEntity> findAllByDetail(@Param("name") String name,
                                        @Param("surname") String surname,
                                        @Param("age") Integer age);

    @Query("from StudentEntity where name=?1 and surname=?2 and age=?3")
    List<StudentEntity> findAllByDetailPosition(String name,
                                                String surname,
                                                Integer age);

    @Query(value = "select * from student where name=:name", nativeQuery = true)
    List<StudentEntity> findAllByNameNative(@Param("name") String name);

    @Query(value = "select * from student where name=?1 and surname=?2 and age=?3", nativeQuery = true)
    List<StudentEntity> findAllByDetailNativePosition(String name,
                                                      String surname,
                                                      Integer age);

    @Modifying
    @Transactional
    @Query("update StudentEntity set name=?1 , surname=?2 where id=?3")
    int updateNameAndSurname(String name, String surname, Integer id);

    @Modifying
    @Transactional
    @Query("delete from StudentEntity where name=?1 and surname=?2")
    void deleteByNameAndSurname(String name, String surname);

    @Query("from StudentEntity order by name asc ")
    List<StudentEntity> findAllOrderByName();

    @Query("select s.id, s.name from StudentEntity s")
    List<Object[]> getIdAndNameList();

    @Query("select new StudentEntity (s.id, s.name) from StudentEntity s")
    List<StudentEntity> getIdAndNameListUsingConstructor();

    @Query("select new dasturlash.uz.dto.StudentShortInfoDTO(s.id, s.name, s.surname) from StudentEntity s")
    List<StudentShortInfoDTO> getStudentUsingNotEntityConstructor();

    @Query("select s.id as id, s.name as name, s.surname as surname from StudentEntity s")
    List<StudentInfoMapper> getStudentInfoList();


    // ========================= PAGINATION ======================

    // select * from student where name=? offset ? limit=?
    // select count(*) from student where name=?
    Page<StudentEntity> findByName(String name, Pageable pageable);

    Page<StudentEntity> findByNameAndSurname(String name, String surname, Pageable pageable);

    @Query("select s from StudentEntity s where s.name=?1 and s.age=?2")
    Page<StudentEntity> findAllNameAndAge(String name, Integer age, Pageable pageable);

    Page<StudentEntity> findByLevel(Integer level, Pageable pageable);

}
