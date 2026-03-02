package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.RoleResponseDto;
import BankApp.SpringBank.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles/v1")
public class RoleApi {

    private final RoleService service;

    @GetMapping
    public List<RoleResponseDto> get(){
        return service.get();
    }
}
