package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.res.user.ProfileResponseDto;
import BankApp.SpringBank.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    ProfileResponseDto toDto(User user);
}
