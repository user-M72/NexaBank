package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.req.UserRequestDto;
import BankApp.SpringBank.dto.res.UserResponseDto;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto requestDto, Set<Role> roles, @MappingTarget User user);
}
