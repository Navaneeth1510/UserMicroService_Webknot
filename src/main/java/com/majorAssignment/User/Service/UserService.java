package com.majorAssignment.User.Service;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.majorAssignment.User.Dto.MetroCardDto;
import com.majorAssignment.User.Entity.MetroCardEntity;
import com.majorAssignment.User.Entity.TravelHistoryEntity;
import com.majorAssignment.User.Entity.UserEntity;
import com.majorAssignment.User.Repository.MetroCardRepository;
import com.majorAssignment.User.Repository.TravelHistoryRepository;
import com.majorAssignment.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MetroCardRepository metroCardRepository;

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    // Buy Metro Card
    public MetroCardEntity buyMetroCard(MetroCardDto CardPayload) {
        Optional<UserEntity> user = userRepository.findById(CardPayload.getUserId());
        if (user.isPresent()) {
            UserEntity newuser = user.get();
            MetroCardEntity metroCard = new MetroCardEntity();
            metroCard.setCardNumber(CardPayload.getCardNumber());
            metroCard.setBalance(CardPayload.getInitialBalance());
            metroCard.setUser(newuser);
            return metroCardRepository.save(metroCard);
        }
        throw new RuntimeException("User not found");
    }

    // Retrieve Travel History (Last 10 Trips)
    public List<TravelHistoryEntity> getTravelHistory(Long userId) {
        return travelHistoryRepository.findTop10ByUserIdOrderByTravelTimeDesc(userId);
    }

    // Cancel Metro Pass
    public String cancelMetroPass(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserEntity newuser = user.get();
            if (newuser.getMetroCard() != null) {
                MetroCardEntity metroCard = newuser.getMetroCard();

                // **Remove association first**
                newuser.setMetroCard(null);
                userRepository.save(newuser);  // **Update the user to break the relationship**
                System.out.println("Deleting metro card with ID: " + metroCard.getId());
                metroCardRepository.delete(metroCard);  // **Now delete the metro card**
                return "Metro pass canceled successfully.";
            } else {
                return "User does not have an active metro pass.";
            }
        }
        return "User not found.";
    }

    // Fetch User Profile
    @JsonIgnore
    public Optional<UserEntity> getUserProfile(Long userId) {
        return userRepository.findById(userId);
    }
}

