package com.pucminas.bubble_app_back.core.user;

import com.pucminas.bubble_app_back.dataprovider.user.IUserRepository;
import com.pucminas.bubble_app_back.model.user.User;
import com.pucminas.bubble_app_back.common.enums.UsersRole;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
    private static final int PASSWORD_LENGTH = 12;

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public void salvarUsuariosDoExcel(MultipartFile file) throws IOException {
        List<User> usuarios = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            User usuario = new User();
            usuario.setName(row.getCell(0).getStringCellValue());
            usuario.setEmail(row.getCell(1).getStringCellValue());
            usuario.setActive(true);
            usuario.setRole(UsersRole.STUDENT);

            String randomPassword = generateRandomPassword();
            usuario.setPassword(randomPassword);

            sendEmail(usuario, randomPassword);
            usuarios.add(usuario);
        }

        userRepository.saveAll(usuarios);
    }

    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + user.getEmail());
        }
        user.setPassword(generateRandomPassword());
        userRepository.save(user);
        sendEmail(user, user.getPassword());
        return user;
    }


    public User editPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private void sendEmail(User usuario, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(usuario.getEmail());
            helper.setSubject("Welcome to Bubble School!");
            helper.setText("Olá " + usuario.getName() + ",\n\nBem-vindo(a) ao nosso aplicativo!\n\nSua senha temporária é: "
                    + password + "\n\nPor favor, acesse o sistema e altere sua senha.", true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsersByEmail(List<String> emails) {
        return userRepository.findAllByEmail(emails);
    }

    public List<User> getUsersByRole(UsersRole role) {
        return userRepository.findByRole(role);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            existingUser.setName(updatedUser.getName() != null ? updatedUser.getName() : existingUser.getName());
            existingUser.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : existingUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword() != null ? updatedUser.getPassword() : existingUser.getPassword());
            existingUser.setRole(updatedUser.getRole() != null ? updatedUser.getRole() : existingUser.getRole());
            existingUser.setActive(updatedUser.getActive() != null ? updatedUser.getActive() : existingUser.getActive());
            existingUser.setFirstAcess(updatedUser.getFirstAcess() != null ? updatedUser.getFirstAcess() : existingUser.getFirstAcess());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}
