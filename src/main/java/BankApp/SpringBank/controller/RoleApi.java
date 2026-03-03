package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.role.RoleRequestDto;
import BankApp.SpringBank.dto.res.role.RoleResponseDto;
import BankApp.SpringBank.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles/v1")
public class RoleApi {

    private final RoleService service;

    @GetMapping
    public List<RoleResponseDto> get(){
        return service.get();
    }

    @GetMapping("/{roleId}")
    public RoleResponseDto getById(@PathVariable("roleId")UUID id){
        return service.getById(id);
    }

    @PostMapping()
    public ResponseEntity<RoleResponseDto> created(@RequestBody RoleRequestDto dto){
        RoleResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{roleId}")
    public RoleResponseDto updated(@PathVariable("roleId") UUID id,
                                   @RequestBody RoleRequestDto dto){
        return service.updated(id, dto);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleted(@PathVariable("roleId") UUID id){
        service.deleted(id);
        return ResponseEntity.ok().build();
    }
}
