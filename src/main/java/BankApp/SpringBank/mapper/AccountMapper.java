package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountResponseDto toDto(Account account);

    Account toEntity(AccountRequestDto dto, User user, String accountNumber);

    void updateFromDto(AccountRequestDto dto, @MappingTarget Account account);
}
