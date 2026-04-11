package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transfer.TransferRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;

import java.util.List;

public interface TransactionService {

    TransactionResponseDto transfer(TransferRequestDto dto);

    TransactionResponseDto deposit(DepositRequestDto dto);

    TransactionResponseDto withdraw(DepositRequestDto dto);

    List<TransactionResponseDto> getHistory();

}
