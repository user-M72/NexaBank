package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "firstName", source = "requestDto.firstName")
    @Mapping(target = "lastName", source = "requestDto.lastName")
    User toEntity(UserRequestDto requestDto, Set<Role> roles, String password);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "firstName", source = "dto.firstName")
    @Mapping(target = "lastName", source = "dto.lastName")
    @Mapping(target = "roles", source = "roles")
    void updateFromDto(UserRequestDto dto, Set<Role> roles, @MappingTarget User user);
}
