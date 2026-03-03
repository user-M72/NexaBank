package BankApp.SpringBank.mapper;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

    CardResponseDto toDto(Card card);

    Card toEntity(CardRequestDto dto);
}
