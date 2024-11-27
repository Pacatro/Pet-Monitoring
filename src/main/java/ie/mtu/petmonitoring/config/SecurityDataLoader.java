package ie.mtu.petmonitoring.config;

import ie.mtu.petmonitoring.model.Privilege;
import ie.mtu.petmonitoring.model.Role;
import ie.mtu.petmonitoring.model.User;
import ie.mtu.petmonitoring.repository.PrivilegeRepository;
import ie.mtu.petmonitoring.repository.RoleRepository;
import ie.mtu.petmonitoring.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class SecurityDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    public SecurityDataLoader(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        // Create privileges
        Privilege readPetPrivilege = createPrivilegeIfNotFound("READ_PET");
        Privilege writePetPrivilege = createPrivilegeIfNotFound("WRITE_PET");
        Privilege readHouseholdPrivilege = createPrivilegeIfNotFound("READ_HOUSEHOLD");
        Privilege writeHouseholdPrivilege = createPrivilegeIfNotFound("WRITE_HOUSEHOLD");

        // Create roles
        Role adminRole = createRoleIfNotFound("ADMIN",
                readPetPrivilege, writePetPrivilege,
                readHouseholdPrivilege, writeHouseholdPrivilege);
        Role userRole = createRoleIfNotFound("USER",
                readPetPrivilege, writePetPrivilege);

        // Create users
        createUserIfNotFound("admin", "admin123", adminRole);
        createUserIfNotFound("user", "user123", userRole);

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> privilege = privilegeRepository.findByName(name);
        return privilege.orElseGet(() -> {
            Privilege newPrivilege = new Privilege(name);
            return privilegeRepository.save(newPrivilege);
        });
    }

    @Transactional
    public Role createRoleIfNotFound(String name, Privilege... privileges) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElseGet(() -> {
            Role newRole = new Role(name);
            for (Privilege privilege : privileges) {
                newRole.addPrivilege(privilege);
            }
            return roleRepository.save(newRole);
        });
    }

    @Transactional
    public void createUserIfNotFound(String username, String password, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User(username, passwordEncoder.encode(password));
            user.addRole(role);
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}