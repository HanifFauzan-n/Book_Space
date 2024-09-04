package com.library.jafa.services.member;

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
import com.library.jafa.dao.member.MemberDao;
import com.library.jafa.dto.IdentityResponseDto;
import com.library.jafa.dto.PageResponse;
import com.library.jafa.dto.RegistrationDto;
import com.library.jafa.entities.Member;
import com.library.jafa.entities.Roles;
import com.library.jafa.entities.Users;
import com.library.jafa.repositories.MemberRepository;
import com.library.jafa.repositories.RoleRepository;
import com.library.jafa.repositories.UserRepository;
import com.library.jafa.services.auth.EmailService;

@Service
public class MemberServiceImp implements MemberService {

    @Autowired
    RoleRepository rolesRepository;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberDao memberDao;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public IdentityResponseDto register(RegistrationDto dto) {
        validasi(dto);
        Users user = saveUsers(dto);
        saveMember(dto, user);
        IdentityResponseDto regist = new IdentityResponseDto().response(dto, user);
        sendEmail(dto.getEmail(), dto);
        return regist;
    }

    private void validasi(RegistrationDto dto) {
        // Name validation
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty.");
        }
        if (memberRepository.findByMemberName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Detected identical name.");
        }
        if (dto.getName().length() < 2 || dto.getName().length() > 45) {
            throw new IllegalArgumentException("Member name length must be between 2 and 45 characters.");
        }
        // Check allowed characters (only letters, spaces, quotes, and hyphens)
        if (!dto.getName().matches("^[a-zA-Z\\s'-]*$")) {
            throw new IllegalArgumentException(
                    "Member name can only consist of letters, spaces, quotes, and hyphens.");
        }
    
        // Age validation
        if (dto.getAge() < 18 || dto.getAge() > 50) {
            throw new IllegalArgumentException("Member age must be between 18 and 50 years.");
        }
        // Gender validation
        if (dto.getGender() == null || dto.getGender().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty.");
        }
        if (!dto.getGender().equalsIgnoreCase("Female") && !dto.getGender().equalsIgnoreCase("Male")) {
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
        // Password validation
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
        Roles memberRoles = rolesRepository.findByRoleName(RoleConstant.MEMBER_ROLE);
        users.setRole(memberRoles);
        return usersRepository.save(users);
    }

    private Member saveMember(RegistrationDto dto, Users users) {
        Member member = new Member();
        member.setMemberName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setAddress(dto.getAddress());
        member.setGender(dto.getGender());
        member.setMemberAge(dto.getAge());
        member.setUser(users);
        return memberRepository.save(member);

    }

    @Override
    public String removeMember(String id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            Users users = usersRepository.findByUserName(member.getEmail()).orElse(null);
            memberRepository.delete(member);
            usersRepository.delete(users);

            return "Success Remove member ";
        } else {
            return "Member ID not found";
        }
    }

    @Override
    public IdentityResponseDto updateMember(String id, RegistrationDto dto) {
        validasi(dto);
        Member member = memberRepository.findById(id).orElse(null);
        Users users = usersRepository.findById(member.getUser().getId()).orElse(null);
        member.setMemberName(dto.getName());
        member.setMemberAge(dto.getAge());
        member.setGender(dto.getGender());
        member.setAddress(dto.getAddress());
        member.setEmail(dto.getEmail());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        users.setUserName(dto.getEmail());
        users.setRole(users.getRole());
        usersRepository.save(users);
        memberRepository.save(member);
        IdentityResponseDto identity = new IdentityResponseDto().response(dto, users);
        return identity;
    }

    private void sendEmail(String to, RegistrationDto dto) {
        String subject = "Library Member Registration";
        String text = "<html><body style='background-color: #d3f7d3; padding: 20px;'><div style='background-color: white; padding: 20px; border-radius: 10px;'>"
        + "<h2 style='color: #007bff;'>Dear Library Member,</h2>"
        + "<p>The library member registration process has been successfully completed. Please don't forget the password: <strong>"
        + dto.getPassword() + "</strong>. Further information will be provided gradually.</p>"
        + "<p>Thank you.</p>"
        + "<p>Regards,<br/>Officer</p></div></body></html>";
        emailService.sendSimpleMessage(to, subject, text);
    }

    @Override
    public PageResponse<Member> findAll(String memberName, String addres, Integer memberAge, int page, int size,
            String sortBy, String sortOrder) {
        return memberDao.findAll(memberName, addres, memberAge, page, size, sortBy, sortOrder);
    }

    @Override
    public void uploadMemberPhoto(String id, MultipartFile photo)
            throws IOException, SQLException {
        String[] filename = Objects.requireNonNull(photo.getResource().getFilename()).split("\\.");
        if (!filename[filename.length - 1].equalsIgnoreCase("jpg")
                && !filename[filename.length - 1].equalsIgnoreCase("jpeg")
                && !filename[filename.length - 1].equalsIgnoreCase("png")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported filetype");
        }

        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            member.setPhotoMember(new SerialBlob(photo.getBytes()));
            memberRepository.save(member);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member notFound");
        }
    }

    // public void uploadStudentPhoto(String studentId, MultipartFile studentPhoto)
    // throws IOException, SQLException {
    // String[] filename =
    // Objects.requireNonNull(studentPhoto.getResource().getFilename()).split("\\.");
    // if (!filename[filename.length - 1].equalsIgnoreCase("jpg")
    // && !filename[filename.length - 1].equalsIgnoreCase("jpeg")
    // && !filename[filename.length - 1].equalsIgnoreCase("png")) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file
    // type");
    // }
    // System.out.println(filename);

    // Student student = memberRepository.findById(studentId).orElse(null);
    // if (student != null) {
    // student.setPhoto(new SerialBlob(studentPhoto.getBytes()));
    // memberRepository.save(student);
    // } else {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student not
    // Found");
    // }
    // }

    // public void uploadParentPhoto(String parentId, MultipartFile parentPhoto)
    // throws IOException, SQLException {
    // String[] filename =
    // Objects.requireNonNull(parentPhoto.getResource().getFilename()).split("\\.");
    // if (!filename[filename.length - 1].equalsIgnoreCase("jpg")
    // && !filename[filename.length - 1].equalsIgnoreCase("jpeg")
    // && !filename[filename.length - 1].equalsIgnoreCase("png")) {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file
    // type");
    // }
    // Parent parent = parentRepository.findById(parentId).orElse(null);
    // if (parent != null) {
    // parent.setPhoto(new SerialBlob(parentPhoto.getBytes()));
    // parentRepository.save(parent);
    // } else {
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent not
    // Found");
    // }
    // }

}
