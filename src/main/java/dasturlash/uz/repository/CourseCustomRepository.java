package dasturlash.uz.repository;

import dasturlash.uz.dto.CourseFilterDTO;
import dasturlash.uz.dto.PageResponse;
import dasturlash.uz.entity.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public PageResponse<CourseEntity> filter(CourseFilterDTO filter, int page, int size) {
        StringBuilder query = new StringBuilder(" where 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) {
            query.append(" and c.id=:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.append(" and lower(c.name) like :name ");
            params.put("name", "%" + filter.getName() + "%");
        }
        if (filter.getPriceFrom() != null && filter.getPriceTo() != null) {
            query.append(" and c.price between :priceFrom and :priceTo ");
            params.put("priceFrom", filter.getPriceFrom());
            params.put("priceTo", filter.getPriceTo());
        } else if (filter.getPriceFrom() != null) {
            query.append(" and c.price >= :priceFrom ");
            params.put("priceFrom", filter.getPriceFrom());
        } else if (filter.getPriceTo() != null) {
            query.append(" and c.price <= :priceTo ");
            params.put("priceTo", filter.getPriceTo());
        }
        if (filter.getDurationFrom() != null && filter.getDurationTo() != null) {
            query.append(" and c.duration between :durationFrom and :durationTo ");
            params.put("durationFrom", filter.getDurationFrom());
            params.put("durationTo", filter.getDurationTo());
        } else if (filter.getDurationFrom() != null) {
            query.append(" and c.duration >= :durationFrom ");
            params.put("durationFrom", filter.getDurationFrom());
        } else if (filter.getDurationTo() != null) {
            query.append(" and c.duration <= :durationTo ");
            params.put("durationTo", filter.getDurationTo());
        }
        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
            query.append(" and c.createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN));
            params.put("createdDateTo", LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MAX));
        } else if (filter.getCreatedDateFrom() != null) {
            query.append(" and c.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN));
        } else if (filter.getCreatedDateTo() != null) {
            query.append(" and c.createdDate <= :createdDateTo ");
            params.put("createdDateTo", LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX));
        }

        StringBuilder selectBuilder = new StringBuilder(" select c from CourseEntity c ");
        selectBuilder.append(query);
        selectBuilder.append(" order by c.createdDate desc ");

        StringBuilder countBuilder = new StringBuilder(" select c from CourseEntity c ");
        countBuilder.append(query);

        Query selectQuery = entityManager.createQuery(query.toString(), CourseEntity.class);
        Query countQuery = entityManager.createQuery(countBuilder.toString(), CourseEntity.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(),entry.getValue());
        }

        selectQuery.setFirstResult(page* size);
        selectQuery.setMaxResults(size);

        List<CourseEntity> content = selectQuery.getResultList();

        Long totalCount = (Long) countQuery.getSingleResult();
        return new PageResponse<>(content,totalCount);
    }
}
