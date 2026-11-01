package BankApp.SpringBank.util;

import BankApp.SpringBank.dto.req.role.RoleRequestDto;
import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.service.RoleService;
import BankApp.SpringBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbPopulator implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Value("${spring.app.admin.username}")
    private String adminUsername;

    @Value("${spring.app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        createdAdminRole();
        createdAdmin();
        createDemoAdmin();
        createUserRole();
        createUser();
    }

    private void createdAdminRole() {
        if (!roleService.existsByName("ADMIN")) {
            roleService.create(new RoleRequestDto("ADMIN", "Administrator"));
        }
    }

    private void createdAdmin() {
        if (!userService.existsByEmail("admin@admin.com")) {
            Role admin = roleService.getByName("ADMIN");
            userService.create(new UserRequestDto(
                    "Admin",
                    "Admin",
                    adminUsername,
                    "admin@admin.com",
                    adminPassword,
                    List.of(admin.getId())
            ));
        }
    }

    private void createUserRole() {
        if (!roleService.existsByName("USER")){
            roleService.create(new RoleRequestDto("USER", "Uses the app"));
        }
    }

    private void createUser(){
            if (!userService.existsByEmail("user@user.com")) {
                Role user = roleService.getByName("USER");
                userService.create(new UserRequestDto(
                        "User",
                        "User",
                        "demoUser",
                        "user@user.com",
                        "demoUser",
                        List.of(user.getId())
                ));
            }
        }

    private void createDemoAdmin(){
        if (!userService.existsByEmail("demoAdmin@admin.com")){
            Role demoAdmin = roleService.getByName("ADMIN");
            userService.create(new UserRequestDto(
                    "Demo-admin",
                    "Demo-admin",
                    "demoAdmin",
                    "demoAdmin@admin.com",
                    "demoAdmin",
                    List.of(demoAdmin.getId())
            ));
        }
    }
}
