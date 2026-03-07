package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/v1")
public class UserApi {

    private final UserService service;

    @GetMapping
    public List<UserResponseDto> get(){
        return service.get();
    }

    @GetMapping("/{userId}")
    public UserResponseDto getById(@PathVariable("userId")UUID id){
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> created(@Valid @RequestBody UserRequestDto dto){
        UserResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updated(@Valid @PathVariable("userId") UUID id,
                                   @RequestBody UserRequestDto dto){
        return service.updated(id, dto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleted(@PathVariable("userId") UUID id){
         service.deleted(id);
         return ResponseEntity.ok().build();
    }
}
