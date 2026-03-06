package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(target = "fromAccountId", source = "fromAccount.id")
    @Mapping(target = "toAccountId", source = "toAccount.id")
    TransactionResponseDto toDto(Transaction transaction);

    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "amount", source = "dto.amount")
    @Mapping(target = "description", source = "dto.description")
    Transaction toEntity(TransactionRequestDto dto, Account fromAccount, Account toAccount, String referenceNumber);
}
