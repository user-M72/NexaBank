package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/v1")
@Tag(name = "User API", description = "API for managing users in the banking application")
public class UserApi {

    private final UserService service;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @GetMapping
    public List<UserResponseDto> get(){
        return service.get();
    }

    @Operation(summary = "Get by id users", description = "Get all users in the system by id")
    @GetMapping("/{userId}")
    public UserResponseDto getById(@PathVariable("userId")UUID id){
        return service.getById(id);
    }

    @Operation(summary = "Create a users", description = "Create a new user in the system with the provided details")
    @PostMapping
    public ResponseEntity<UserResponseDto> created(@Valid @RequestBody UserRequestDto dto){
        UserResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a users", description = "Update an existing user in the system with the provided details")
    @PutMapping("/{userId}")
    public UserResponseDto updated(@Valid @PathVariable("userId") UUID id,
                                   @RequestBody UserRequestDto dto){
        return service.updated(id, dto);
    }

    @Operation(summary = "Delete a users", description = "Delete an existing user in the system by id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleted(@PathVariable("userId") UUID id){
         service.deleted(id);
         return ResponseEntity.ok().build();
    }
}
