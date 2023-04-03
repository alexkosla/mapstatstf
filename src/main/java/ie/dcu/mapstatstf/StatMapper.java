package ie.dcu.mapstatstf;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatMapper {
    @Mapping(source = "subject", target = "title")
    StatModel entityToDto(StatEntity entity);

    @Mapping(source = "title", target = "subject")
    StatEntity toEntity(StatModel model);
}
