package BankApp.SpringBank.util;

import BankApp.SpringBank.dto.req.role.RoleRequestDto;
import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.service.RoleService;
import BankApp.SpringBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbPopulator implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void run(String... args) {
        createdAdminRole();
        createdAdmin();
    }

    private void createdAdminRole() {
        if (!roleService.existsByName("ADMIN")) {
            roleService.created(new RoleRequestDto("ADMIN", "Administrator"));
        }
    }

    private void createdAdmin() {
        if (!userService.existsByEmail("admin@admin.com")) {
            Role admin = roleService.getByName("ADMIN");
            userService.created(new UserRequestDto(
                    "Admin",
                    "Admin",
                    "admin",
                    "admin@admin.com",
                    "admin",
                    List.of(admin.getId())
            ));
        }
    }
}
