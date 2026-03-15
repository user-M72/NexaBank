package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.role.RoleRequestDto;
import BankApp.SpringBank.dto.res.role.RoleResponseDto;
import BankApp.SpringBank.exception.AdminNotFoundException;
import BankApp.SpringBank.exception.RoleNotFoundException;
import BankApp.SpringBank.mapper.RoleMapper;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.repository.RoleRepository;
import BankApp.SpringBank.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    RoleServiceImpl testService;

    @Mock private RoleRepository roleRepository;
    @Mock private RoleMapper mapper;

    private final UUID roleId = UUID.randomUUID();
    private  RoleResponseDto response;
    private RoleRequestDto request;
    private Role role;

    @BeforeEach
    void setup(){

        role = new Role();

        response = new RoleResponseDto(
                roleId,
                "roleName",
                "roleDescription"
        );

        request = new RoleRequestDto(
                "roleName",
                "roleDescription"
        );
    }

    @Test
    void get_shouldWork() {

        List<Role> roles =List.of(role);

        RoleResponseDto response = new RoleResponseDto(
                roleId,
                "roleName",
                "roleDescription"
        );

        List<RoleResponseDto> expected = List.of(response);

        when(roleRepository.findAll()).thenReturn(roles);
        when(mapper.toDto(role)).thenReturn(response);

        List<RoleResponseDto> actual = testService.get();

        assertEquals(expected, actual);

        verify(roleRepository).findAll();
        verify(mapper).toDto(role);
    }

    @Test
    void getById_shouldWork(){

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(mapper.toDto(role)).thenReturn(response);

        RoleResponseDto actual = testService.getById(roleId);

        assertEquals(response, actual);

        verify(roleRepository).findById(roleId);
        verify(mapper).toDto(role);
    }

    @Test
    void getById_shouldThrow_whenRoleNotFound(){
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class,
                ()-> testService.getById(roleId));

        verify(roleRepository).findById(roleId);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void create_shouldWork(){

        when(mapper.toEntity(request)).thenReturn(role);
        when(roleRepository.save(role)).thenAnswer(i -> i.getArgument(0));
        when(mapper.toDto(role)).thenReturn(response);

        RoleResponseDto actual = testService.create(request);

        assertEquals(response, actual);

        verify(mapper).toEntity(request);
        verify(roleRepository).save(role);
        verify(mapper).toDto(role);
    }

    @Test
    void update_shouldWork(){

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);
        when(mapper.toDto(role)).thenReturn(response);

        RoleResponseDto actual = testService.update(roleId, request);

        assertEquals(response, actual);

        verify(roleRepository).findById(roleId);
        verify(mapper).updateFromDto(request, role);
        verify(roleRepository).save(role);
        verify(mapper).toDto(role);
    }

    @Test
    void update_shouldThrow_RoleNotFound(){

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class,
                ()->testService.update(roleId, request));

        verify(mapper, never()).updateFromDto(any(), any());
        verify(roleRepository, never()).save(any());

    }

    @Test
    void delete_shouldWork(){

        when(roleRepository.existsById(roleId)).thenReturn(true);

        testService.delete(roleId);

        verify(roleRepository).existsById(roleId);
        verify(roleRepository).deleteById(roleId);
    }

    @Test
    void delete_shouldThrow_RoleNotFound(){

        when(roleRepository.existsById(roleId)).thenReturn(false);

        assertThrows(RoleNotFoundException.class,
                ()-> testService.delete(roleId));

        verify(roleRepository).existsById(roleId);

    }

    @Test
    void getByIdList_shouldWork(){

        List<UUID> idList = List.of(roleId, UUID.randomUUID());
        List<Role> roles = List.of(role, new Role());

        when(roleRepository.findAllById(idList)).thenReturn(roles);

        Set<Role> actual = testService.getByIdList(idList);

        assertEquals(new HashSet<>(roles), actual);
        verify(roleRepository).findAllById(idList);

    }

    @Test
    void getByIdList_shouldReturnEmptySet_whenNoRolesFound(){

        List<UUID> idList = List.of(roleId, UUID.randomUUID());

        when(roleRepository.findAllById(idList)).thenReturn(List.of());

        Set<Role> actual = testService.getByIdList(idList);

        assertTrue(actual.isEmpty());
        verify(roleRepository).findAllById(idList);
    }

    @Test
    void getByName_shouldWork(){

        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));

        Role actual = testService.getByName(role.getName());

        assertEquals(role, actual);

        verify(roleRepository).findByName(role.getName());

    }

    @Test
    void getByName_shouldThrow_adminNotFound(){

        when(roleRepository.findByName(role.getName())).thenReturn(Optional.empty());

        assertThrows(AdminNotFoundException.class,
                ()-> testService.getByName(role.getName()));

        verify(roleRepository).findByName(role.getName());

    }
}
