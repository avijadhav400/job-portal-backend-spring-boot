package com.jobportal.service;

import com.jobportal.entity.Role;
import com.jobportal.entity.Status;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> pendingRecruiters(){
        return userRepository.findByRoleAndStatus(Role.RECRUITER, Status.PENDING);
    }

    public void approveRecruiter(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }
}
