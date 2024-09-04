package com.library.jafa.services.officer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.rowset.serial.SerialBlob;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.library.jafa.constans.RoleConstant;
import com.library.jafa.dao.officer.OfficerDao;
import com.library.jafa.dto.IdentityResponseDto;
import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.RegistrationDto;
import com.library.jafa.entities.LibraryOfficer;
import com.library.jafa.entities.Roles;
import com.library.jafa.entities.Users;
import com.library.jafa.repositories.OfficerRepository;
import com.library.jafa.repositories.RoleRepository;
import com.library.jafa.repositories.UserRepository;
import com.library.jafa.services.auth.EmailService;

@Service
public class OfficerServiceImp implements OfficerService {

    @Autowired
    RoleRepository rolesRepository;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    OfficerRepository officerRepository;

    @Autowired
    OfficerDao officerDao;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public IdentityResponseDto register(RegistrationDto dto) {
        validasi(dto);
        Users user = saveUsers(dto);
        saveOfficer(dto, user);
        IdentityResponseDto regist = new IdentityResponseDto().response(dto, user);
        sendEmail(dto.getEmail(), dto);
        return regist;
    }

    private void validasi(RegistrationDto dto) {
        // Validation for name
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Officer name cannot be empty.");
        }
        if (dto.getName().length() < 2 || dto.getName().length() > 40) {
            throw new IllegalArgumentException("Officer name length must be between 2 and 40 characters.");
        }
        // Check allowed characters (only letters, spaces, quotes, and hyphens)
        if (!dto.getName().matches("^[a-zA-Z\\s'-]*$")) {
            throw new IllegalArgumentException(
                    "Officer name can only consist of letters, spaces, quotes, and hyphens.");
        }
    
        // Validation for age
        if (dto.getAge() < 18 || dto.getAge() > 50) {
            throw new IllegalArgumentException("Officer age must be between 18 and 50 years.");
        }
        // Validation for gender
        if (dto.getGender() == null || dto.getGender().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty.");
        }
        if (!dto.getGender().equals("Female") && !dto.getGender().equals("Male")) {
            throw new IllegalArgumentException("Gender must be 'Female' or 'Male'.");
        }
    
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
    
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!dto.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (usersRepository.findByUserName(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use. Please use another email.");
        }
        // Validation for password
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (dto.getPassword().length() < 8 || dto.getPassword().length() > 12) {
            throw new IllegalArgumentException("Password length must be between 8 and 12 characters.");
        }
        if (dto.getPassword().contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }
    }
    

    private Users saveUsers(RegistrationDto dto) {
        Users users = new Users();
        users.setUserName(dto.getEmail());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        // kirim password melalui email
        Roles officerRole = rolesRepository.findByRoleName(RoleConstant.OFFICER_ROLE);
        users.setRole(officerRole);
        return usersRepository.save(users);
    }

    private LibraryOfficer saveOfficer(RegistrationDto dto, Users users) {
        LibraryOfficer officer = new LibraryOfficer();
        officer.setOfficerName(dto.getName());
        officer.setGender(dto.getGender());
        officer.setOfficerAge(dto.getAge());
        officer.setOfficerAddress(dto.getAddress());
        officer.setEmail(dto.getEmail());
        officer.setUsers(users);
        return officerRepository.save(officer);

    }

    private void sendEmail(String to, RegistrationDto dto) {
        String subject = "Officer Registration";
        String text = "<html><body style='background-color: #d3f7d3; padding: 20px;'><div style='background-color: white; padding: 20px; border-radius: 10px;'>"
        + "<h2 style='color: #007bff;'>Dear Library Officer,</h2>"
        + "<p>The library officer registration process has been successfully completed. Please don't forget the password: <strong>"
        + dto.getPassword() + "</strong>. Further information will be provided gradually.</p>"
        + "<p>Thank you.</p>"
        + "<p>Regards,<br/>Admin</p></div></body></html>";
        emailService.sendSimpleMessage(to, subject, text);
    }

    @Override
    public String removeOfficer(String id) {
        LibraryOfficer officer = officerRepository.findById(id).orElse(null);
        if (officer != null) {
            Users users = usersRepository.findByUserName(officer.getEmail()).orElse(null);
            officerRepository.delete(officer);
            usersRepository.delete(users);
            return "Success Remove officer ";
        } else {
            return "Officer ID not found ";
        }
    }

    @Override
    public IdentityResponseDto updateOfficer(String id, RegistrationDto dto){
        validasi(dto);
        LibraryOfficer officer = officerRepository.findById(id).orElse(null);
        Users users = usersRepository.findById(officer.getUsers().getId()).orElse(null);
        officer.setOfficerName(dto.getName());
        officer.setOfficerAge(dto.getAge());
        officer.setGender(dto.getGender());
        officer.setOfficerAddress(dto.getAddress());
        officer.setEmail(dto.getEmail());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        users.setUserName(dto.getEmail());
        users.setRole(users.getRole());
        usersRepository.save(users);
        officerRepository.save(officer);
        IdentityResponseDto identity = new IdentityResponseDto().response(dto, users);
        return identity;
    }

        @Override
    public PageResponse<LibraryOfficer> findAll(String officerName, String officerAddres, Integer officerAge, int page, int size,
            String sortBy, String sortOrder) {
        return officerDao.findAll(officerName, officerAddres, officerAge, page, size, sortBy, sortOrder);
    }



    @Override
    public void uploadOfficerPhoto(String id, MultipartFile photo)
    throws IOException, SQLException {
        String[] filename = Objects.requireNonNull(photo.getResource().getFilename()).split("\\.");
        if (!filename[filename.length - 1].equalsIgnoreCase("jpg")
                && !filename[filename.length - 1].equalsIgnoreCase("jpeg")
                && !filename[filename.length - 1].equalsIgnoreCase("png")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported filetype");
        }
        // System.out.println(filename);

        LibraryOfficer officer2 = officerRepository.findById(id).orElse(null);
        if (officer2 != null) {
            officer2.setPhotoOfficer(new SerialBlob(photo.getBytes()));
            officerRepository.save(officer2);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Officer notFound");
        }
    }

}
