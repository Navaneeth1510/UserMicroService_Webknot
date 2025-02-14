package com.majorAssignment.User.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.majorAssignment.User.Dto.MetroCardDto;
import com.majorAssignment.User.Dto.TravelHistoryDto;
import com.majorAssignment.User.Entity.MetroCardEntity;
import com.majorAssignment.User.Entity.TravelHistoryEntity;
import com.majorAssignment.User.Entity.UserEntity;
import com.majorAssignment.User.Service.TravelHistoryService;
import com.majorAssignment.User.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TravelHistoryService travelService;

    @PostMapping("/buy-metro-card")
    public MetroCardEntity buyMetroCard(@RequestBody MetroCardDto metroCardPayload) {
        logger.info("Request received to buy metro card: {}", metroCardPayload);
        return userService.buyMetroCard(metroCardPayload);
    }

    @GetMapping("/travel-history/{userId}")
    @Cacheable(value = "travelHistory", key = "#userId")
    public CompletableFuture<List<TravelHistoryEntity>> getTravelHistory(@PathVariable Long userId) {
        logger.info("Fetching travel history for userId: {}", userId);
        return travelService.getTravelHistory(userId);
    }

    @DeleteMapping("/cancel-metro-pass/{userId}")
    @CacheEvict(value = {"users", "travelHistory"}, key = "#userId")
    public String cancelMetroPass(@PathVariable Long userId) {
        logger.warn("Cancelling metro pass for userId: {}", userId);
        return userService.cancelMetroPass(userId);
    }

    @GetMapping("/profile/{userId}")
    @Cacheable(value = "users", key = "#userId")
    @JsonIgnore
    public Optional<UserEntity> getUserProfile(@PathVariable Long userId) {
        logger.info("Fetching user profile for userId: {}", userId);
        return userService.getUserProfile(userId);
    }

    @PutMapping("/update-balance/{userId}")
    public ResponseEntity<String> updateBalance(
            @PathVariable Long userId,
            @RequestBody Map<String, Double> balancePayload) {

        Double newBalance = balancePayload.get("newBalance");
        if (newBalance == null) {
            return ResponseEntity.badRequest().body("Balance value is required.");
        }

        String response = userService.updateMetroCardBalance(userId, newBalance);
        return ResponseEntity.ok(response);
    }
}
