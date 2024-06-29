package com.onrender.todolistjeed.todolistapi.Controllers;

import com.onrender.todolistjeed.todolistapi.Models.UserModel;
import com.onrender.todolistjeed.todolistapi.Models.UserUpdateModel;
import com.onrender.todolistjeed.todolistapi.Repositories.iUserRepository;
import com.onrender.todolistjeed.todolistapi.Services.EmailValidatorWithDomainCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private iUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("")
    public ResponseEntity<Integer> createUser(@RequestBody UserModel userModel) {
        try {
            UserModel user = this.userRepository.findByUsername(userModel.getUsername());
            UserModel verifyEmail = this.userRepository.findByEmail(userModel.getEmail());
            boolean verifyEmailDomain = EmailValidatorWithDomainCheck.isValid(userModel.getEmail());

            if (user != null || verifyEmail != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(409);
            }

            if (!verifyEmailDomain) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
            }

            String hashPassword = passwordEncoder.encode(userModel.getPassword());

            userModel.setPassword(hashPassword);

            this.userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(201);

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> readUser() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(200);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
        }
    }

    @PostMapping("/email-verify/{email}")
    public ResponseEntity<Integer> readEmail(@PathVariable String email) {
        try {
            UserModel emailVerify = this.userRepository.findByEmail(email);
            if (emailVerify != null) {
                return ResponseEntity.status(HttpStatus.OK).body(200);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
        }
    }

    @PostMapping("/reset-pass")
    public ResponseEntity<Integer> updatePass(@RequestBody UserUpdateModel userUpdateModel) {
        try {
            UserModel user = this.userRepository.findByEmail(userUpdateModel.getEmail());

            if (user != null) {
                String newHashPassword = passwordEncoder.encode(userUpdateModel.getNewPass());

                user.setPassword(newHashPassword);

                this.userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(200);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
        }
    }
}
