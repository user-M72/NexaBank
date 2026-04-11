package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

//    @Mapping(target = "fromCardId", source = "fromCard.id")
//    @Mapping(target = "toCardId", source = "toCard.id")
    TransactionResponseDto toDto(Transaction transaction);

//     Transaction toEntity(TransactionRequestDto dto, Account fromAccount, Account toAccount, String referenceNumber);
}
