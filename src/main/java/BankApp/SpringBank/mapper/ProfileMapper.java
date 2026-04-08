package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.res.user.ProfileResponseDto;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    @Mapping(target = "roleIds", source = "roles")
    ProfileResponseDto toDto(User user);

    default Set<UUID> mapRoles(Set<Role> roles){
        if (roles == null) return null;

        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }
}
