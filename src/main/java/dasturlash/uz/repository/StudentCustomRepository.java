package dasturlash.uz.repository;

import dasturlash.uz.dto.PageResponse;
import dasturlash.uz.dto.StudentFilterDTO;
import dasturlash.uz.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public PageResponse<StudentEntity> filter(StudentFilterDTO filter, int page, int size) {

        StringBuilder query = new StringBuilder(" where s.visible = true ");
        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) {
            query.append(" and s.id = :id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.append(" and lower(s.name) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if (filter.getSurname() != null) {
            query.append(" and lower(s.surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getLevel() != null) {
            query.append(" and s.level = :level ");
            params.put("level", filter.getLevel());
        }
        if (filter.getAge() != null) {
            query.append(" and s.age = :age ");
            params.put("age", filter.getAge());
        }
        if (filter.getGender() != null) {
            query.append(" and s.gender = :gender ");
            params.put("gender", filter.getGender().name());
        }
        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
            query.append(" and s.createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN));
            params.put("createdDateTo", LocalDateTime.of(filter.getCreatedDateTo(),LocalTime.MAX));
        } else if (filter.getCreatedDateFrom() != null) {
            query.append(" and s.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", LocalDateTime.of(filter.getCreatedDateFrom(),LocalTime.MIN));
        } else if (filter.getCreatedDateTo() != null) {
            query.append(" and s.createdDate <= :createdDateTo ");
            params.put("createdDateTo", LocalDateTime.of(filter.getCreatedDateTo(),LocalTime.MAX));
        }

//        query.append(" order by s.createdDate desc limit :limit offset :offset");
        StringBuilder selectBuilder = new StringBuilder("select s from StudentEntity s ");
        selectBuilder.append(query);
        selectBuilder.append(" order by s.createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("select count(s) from StudentEntity s ");
        countBuilder.append(query);

        // Content
        Query selectQuery = entityManager.createQuery(selectBuilder.toString(), StudentEntity.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }

//        params.forEach(selectQuery::setParameter); // forning o'rniga ishlatish mumkin.
//        selectQuery.setParameter("offset",page*size);
//        selectQuery.setParameter("limit",size);
        selectQuery.setFirstResult(page * size); // offset page*size
        selectQuery.setMaxResults(size); // limit size

        List<StudentEntity> content = selectQuery.getResultList();


        // Total Count
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) countQuery.getSingleResult();
        return new PageResponse<StudentEntity>(content, totalCount);
    }

    public PageResponse<StudentEntity> filterNative(StudentFilterDTO filter, int page, int size) {
        StringBuilder query = new StringBuilder(" where s.visible = true ");
        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) {
            query.append(" and s.id = :id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.append(" and lower(s.name) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if (filter.getSurname() != null) {
            query.append(" and lower(s.surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getAge() != null) {
            query.append(" and s.age = :age ");
            params.put("age", filter.getAge());
        }
        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
            query.append(" and s.createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
            params.put("createdDateTo", filter.getCreatedDateTo());
        } else if (filter.getCreatedDateFrom() != null) {
            query.append(" and s.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        } else if (filter.getCreatedDateTo() != null) {
            query.append(" and s.createdDate <= :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }

//        query.append(" order by s.createdDate desc limit :limit offset :offset");
        StringBuilder selectBuilder = new StringBuilder("select s from student s ");
        selectBuilder.append(query);
        selectBuilder.append(" order by s.createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("select count(*) from student s ");
        countBuilder.append(query);

        // Content
        Query selectQuery = entityManager.createNativeQuery(selectBuilder.toString(), StudentEntity.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }

//        params.forEach(selectQuery::setParameter); // forning o'rniga ishlatish mumkin.
//        selectQuery.setParameter("offset",page*size);
//        selectQuery.setParameter("limit",size);
        selectQuery.setFirstResult(page * size); // offset page*size
        selectQuery.setMaxResults(size); // limit size

        List<StudentEntity> content = selectQuery.getResultList();


        // Total Count
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) countQuery.getSingleResult();
        return new PageResponse<StudentEntity>(content, totalCount);
    }

}
