package com.vanrait6.api_learn9.Services;

import com.vanrait6.api_learn9.DTO.DTO_user;
import com.vanrait6.api_learn9.Model.User;
import com.vanrait6.api_learn9.Repo.postRepo;
import com.vanrait6.api_learn9.Repo.userRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {
    @Autowired
    private userRepo repo;

    public User findUsernameAndProfile(String emailFollowingDto) {
        return repo.findUsernameAndProfile(emailFollowingDto);
    }

    public boolean existsUserByEmail(String emailDTO) {
        return repo.existsUserByEmail(emailDTO);
    }

    public User register(User user) {
        return  repo.save(user);
    }



    public User addInformationUser(User user) {
        return repo.save(user);
    }

    public User findByEmail(String userEmail) {
        return repo.findByEmail(userEmail);
    }

    public DTO_user getInformationUserBy_Email(String emailUser) {
        User user = repo.findByEmail(emailUser);

        if (user == null) return null;

        DTO_user dto = new DTO_user();
        dto.setBioDTO(user.getBio());
        dto.setGenderDTO(user.getGender());
        dto.setAddressDTO(user.getAddress());
        dto.setDateOfBirthDTO(user.getDateOfBirth().toString());

        return dto;
    }


    public void saveUpdate(User user) {
        repo.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return repo.findUserByEmail(email);
    }
}
