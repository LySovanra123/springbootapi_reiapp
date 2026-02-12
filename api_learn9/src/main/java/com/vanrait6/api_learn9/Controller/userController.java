package com.vanrait6.api_learn9.Controller;

import com.vanrait6.api_learn9.DTO.DTO_user;
import com.vanrait6.api_learn9.Model.User;
import com.vanrait6.api_learn9.Services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class userController {

    @Autowired
    private userService service;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody DTO_user request){
        try{
            User user = new User();
            if(service.existsUserByEmail(request.getEmailDTO())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Message","Email is already registered."));
            }

            user.setUsername(request.getUsernameDTO());
            user.setEmail(request.getEmailDTO());
            user.setPassword(passwordEncoder.encode(request.getPasswordDTO()));
            if(request.getDateCreateDTO()!=null && !request.getDateCreateDTO().isEmpty()){
                try {
                    user.setDateCreate(Date.valueOf(request.getDateCreateDTO()));
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("Message","Invalid date format"));
                }
            }


            User saveUsed = service.register(user);
            saveUsed.setPassword(null);
            return ResponseEntity.ok(Map.of(
                    "Message","Registration successful",
                    "User:",saveUsed
            ));

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
              "Error register user: "+ex.getMessage()
            );
        }
    }

    @PutMapping("/addInformation")
    public ResponseEntity<?> addInformation(@RequestBody DTO_user request){
        try {
            User user = service.findByEmail(request.getEmailDTO());
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("Message","User not found"));
            }

            if(request.getProfileDTO()!=null && !request.getProfileDTO().isEmpty()){
                user.setProfile(Base64.getDecoder().decode(request.getProfileDTO()));
            }

            if(request.getDateOfBirthDTO()!=null && !request.getDateOfBirthDTO().isEmpty()){
                try {
                    user.setDateOfBirth(Date.valueOf(request.getDateOfBirthDTO()));
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("Message","Invalid date format"));
                }
            }

            user.setGender(request.getGenderDTO());
            user.setAddress(request.getAddressDTO());
            user.setBio(request.getBioDTO());

            User savedUser = service.addInformationUser(user);
            savedUser.setPassword(null);

            Map<String, Object> response = Map.of(
                    "emailDTO", user.getEmail(),
                    "usernameDTO", user.getUsername(),
                    "bioDTO", user.getBio(),
                    "profileDTO", user.getProfile() != null ? Base64.getEncoder().encodeToString(user.getProfile()) : null,
                    "dateOfBirthDTO", user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : null,
                    "addressDTO", user.getAddress()
            );

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message","Error: " + ex.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody DTO_user request){
        try {
            User user = service.findByEmail(request.getEmailDTO());
            if (user == null || !passwordEncoder.matches(request.getPasswordDTO(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("Message", "Invalid email or password."));
            }

            // Build a DTO or Map to send to Flutter
            Map<String, Object> response = Map.of(
                    "emailDTO", user.getEmail(),
                    "usernameDTO", user.getUsername(),
                    "bioDTO", user.getBio(),
                    "profileDTO", user.getProfile() != null ? Base64.getEncoder().encodeToString(user.getProfile()) : null,
                    "dateOfBirthDTO", user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : null,
                    "addressDTO", user.getAddress()
            );

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + ex.getMessage());
        }
    }

    @GetMapping("/user/{emailUser}")
    public ResponseEntity<?> getInformationUserBy_Email(@PathVariable String emailUser) {
        try {
            DTO_user dto = service.getInformationUserBy_Email(emailUser);

            if (dto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }


    @PutMapping("/updateInformation")
    public ResponseEntity<?> updateInformationUser(@RequestBody DTO_user request){
        try {
            User user = service.findByEmail(request.getEmailDTO());

            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found"));
            }

            user.setBio(request.getBioDTO());
            user.setAddress(request.getAddressDTO());
            user.setDateOfBirth(Date.valueOf(request.getDateOfBirthDTO()));
            user.setGender(request.getGenderDTO());

            service.saveUpdate(user);

            return ResponseEntity.ok(Map.of("message", "User successfully updated"));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changeEmail(@RequestBody DTO_user request) {
        try {
            User user = service.findByEmail(request.getEmailDTO());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("Message", "User not found"));
            }

            if(!passwordEncoder.matches(request.getPasswordDTO(), user.getPassword())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Message", "Passwords don't match"));
            }

            user.setPassword(passwordEncoder.encode(request.getNewPasswordDTO()));

            service.saveUpdate(user);

            return ResponseEntity.ok(Map.of("Message", "Your Password successfully updated"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/UserComment")
    public ResponseEntity<?> getUserComment(@RequestParam String email) {
        try {
            Optional<User> userOpt = service.findUserByEmail(email);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("Message", "User not found"));
            }

            User user = userOpt.get();

            DTO_user dto = new DTO_user();
            dto.setEmailDTO(user.getEmail());
            dto.setUsernameDTO(user.getUsername());
            dto.setProfileDTO(Base64.getEncoder().encodeToString(user.getProfile())); // base64 or image url

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message", "Error: " + e.getMessage()));
        }
    }



}
