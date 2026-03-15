package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.role.RoleRequestDto;
import BankApp.SpringBank.dto.res.role.RoleResponseDto;
import BankApp.SpringBank.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role/v1")
@Tag(name = "Role API", description = "API for managing roles in the banking application")
public class RoleApi {

    private final RoleService service;

    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles in the system")
    @GetMapping
    public List<RoleResponseDto> get(){
        return service.get();
    }

    @Operation(summary = "Get by id roles", description = "Get all roles in the system by id")
    @GetMapping("/{roleId}")
    public RoleResponseDto getById(@PathVariable("roleId")UUID id){
        return service.getById(id);
    }

    @Operation(summary = "Create a role", description = "Create a new role in the system with the provided details")
    @PostMapping()
    public ResponseEntity<RoleResponseDto> created(@RequestBody RoleRequestDto dto){
        RoleResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a role", description = "Update an existing role in the system with the provided details")
    @PutMapping("/{roleId}")
    public RoleResponseDto updated(@PathVariable("roleId") UUID id,
                                   @RequestBody RoleRequestDto dto){
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a role", description = "Delete an existing role in the system by id")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleted(@PathVariable("roleId") UUID id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
