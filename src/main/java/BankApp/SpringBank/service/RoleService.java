package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.role.RoleRequestDto;
import BankApp.SpringBank.dto.res.role.RoleResponseDto;
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

    boolean existsByName(String admin);

    Role getByName(String admin);
}
