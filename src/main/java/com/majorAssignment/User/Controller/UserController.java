package com.majorAssignment.User.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.majorAssignment.User.Dto.MetroCardDto;
import com.majorAssignment.User.Entity.MetroCardEntity;
import com.majorAssignment.User.Entity.TravelHistoryEntity;
import com.majorAssignment.User.Entity.UserEntity;
import com.majorAssignment.User.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/buy-metro-card")
    public MetroCardEntity buyMetroCard(@RequestBody MetroCardDto MetroCardPayload) {
        return userService.buyMetroCard(MetroCardPayload);
    }

    @GetMapping("/travel-history/{userId}")
    public List<TravelHistoryEntity> getTravelHistory(@PathVariable Long userId) {
        return userService.getTravelHistory(userId);
    }

    @DeleteMapping("/cancel-metro-pass/{userId}")
    public String cancelMetroPass(@PathVariable Long userId) {
        return userService.cancelMetroPass(userId);
    }

    @GetMapping("/profile/{userId}")
    @JsonIgnore
    public Optional<UserEntity> getUserProfile(@PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }
}

