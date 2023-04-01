package ie.dcu.mapstatstf;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(source = "subject", target = "title")
    UserModel entityToDto(UserEntity entity);

    @Mapping(source = "title", target = "subject")
    UserEntity toEntity(UserModel userModel);
}
