package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.RoleRequestDto;
import BankApp.SpringBank.dto.res.RoleResponseDto;
import BankApp.SpringBank.exception.AdminNotFoundException;
import BankApp.SpringBank.exception.RoleNotFoundException;
import BankApp.SpringBank.exception.UserNotFoundException;
import BankApp.SpringBank.mapper.RoleMapper;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.repository.RoleRepository;
import BankApp.SpringBank.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Override
    public List<RoleResponseDto> get() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponseDto getById(UUID id) {
        Role role = repository.findById(id)
                .orElseThrow(()-> new RoleNotFoundException(id));
        return mapper.toDto(role);
    }

    @Override
    public RoleResponseDto created(RoleRequestDto dto) {
        Role role = mapper.toEntity(dto);
        Role save = repository.save(role);
        return mapper.toDto(save);
    }

    @Override
    public RoleResponseDto updated(UUID id, RoleRequestDto dto) {
        Role role = repository.findById(id)
                .orElseThrow(()-> new RoleNotFoundException(id));
        mapper.updateFromDto(dto, role);
        Role save = repository.save(role);
        return mapper.toDto(save);
    }

    @Override
    public void deleted(UUID id) {
        if (!repository.existsById(id)){
            throw new RoleNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public Set<Role> geyByIdList(List<UUID> id) {
        return new HashSet<>(repository.findAllById(id));
    }

    @Override
    public boolean existsByName(String admin) {
        return repository.existsByName(admin);
    }

    @Override
    public Role getByName(String admin) {
        return repository.findByName(admin)
                .orElseThrow(()-> new AdminNotFoundException(admin));
    }
}
