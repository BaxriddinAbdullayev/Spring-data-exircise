package dasturlash.uz.mapper;

public interface StudentInfoMapper {
    Integer getId();

    String getName();

    String getSurname();
}

class StudentInfoMapperImpl implements StudentInfoMapper{
    private Integer id;
    private String name;
    private String surname;

    public void setData(Object[] obj){
        id = (Integer) obj[0];
        name = (String) obj[1];
        surname = (String) obj[2];
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }
}
