package com.library.jafa.initial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.library.jafa.constans.RoleConstant;
import com.library.jafa.entities.Admin;
import com.library.jafa.entities.Roles;
import com.library.jafa.entities.Users;
import com.library.jafa.repositories.AdminRepository;
import com.library.jafa.repositories.RoleRepository;
import com.library.jafa.repositories.UserRepository;

@Component
public class InitialData implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Roles> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            Roles member = new Roles(null, "MEMBER", "role as member in application");
            Roles officer = new Roles(null, "OFFICER", "role as officer in application");
            Roles admin = new Roles(null, "ADMIN", "role as admin in application");

            roleRepository.saveAll(List.of(member, officer, admin));
        }
        List<Admin> admins = adminRepository.findAll();
        if (admins.isEmpty()) {
            Users user = new Users(null, "nurjamilahh98@gmail.com", "jafakelompok3","",
            roleRepository.findByRoleName(RoleConstant.ADMIN_ROLE));
            user.setPassword(passwordEncoder.encode("jafakelompok3"));
            Admin admin = new Admin(null, "jafa", "bandung", "089515752383", "haniffauzann55@gmail.com", user);

            userRepository.save(user);
            adminRepository.save(admin);
        }
    }

}
