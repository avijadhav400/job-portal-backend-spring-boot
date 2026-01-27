package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/pending-recruiters")
    public List<User> pendingRecruiters() {
        return adminService.pendingRecruiters();
    }

    @PostMapping("/approve/{id}")
    public void approve(@PathVariable Long id) {
        adminService.approveRecruiter(id);
    }
}

