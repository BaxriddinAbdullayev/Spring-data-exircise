package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseFilterDTO {
    private Integer id;
    private String name;
    private Double priceFrom;
    private Double priceTo;
    private Integer durationFrom;
    private Integer durationTo;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;

}
