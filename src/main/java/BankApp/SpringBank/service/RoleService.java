package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.RoleRequestDto;
import BankApp.SpringBank.dto.res.RoleResponseDto;
import BankApp.SpringBank.model.Role;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService {

    List<RoleResponseDto> get();

    RoleResponseDto getById(UUID id);

    RoleResponseDto created(RoleRequestDto dto);

    RoleResponseDto updated(UUID id, RoleRequestDto dto);

    void deleted(UUID id);

    Set<Role> geyByIdList(List<UUID> id);
}
