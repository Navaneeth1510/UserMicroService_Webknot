package com.majorAssignment.User.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.majorAssignment.User.Dto.MetroCardDto;
import com.majorAssignment.User.Entity.MetroCardEntity;
import com.majorAssignment.User.Entity.UserEntity;
import com.majorAssignment.User.Exceptions.MetroCardAlreadyExistsException;
import com.majorAssignment.User.Exceptions.MetroCardNotFoundException;
import com.majorAssignment.User.Exceptions.UserNotFoundException;
import com.majorAssignment.User.Repository.MetroCardRepository;
import com.majorAssignment.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MetroCardRepository metroCardRepository;

    // Buy Metro Card
    @Transactional
//    @Caching(evict = {
//            @CacheEvict(value = "userProfiles", key = "#CardPayload.userId"),
//            @CacheEvict(value = "metroCards", key = "#CardPayload.userId")
//    })
    public MetroCardEntity buyMetroCard(MetroCardDto CardPayload) {
        logger.info("Buying metro card for user ID: {}", CardPayload.getUserId());

        UserEntity user = userRepository.findById(CardPayload.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found: {}", CardPayload.getUserId());
                    return new UserNotFoundException(CardPayload.getUserId());
                });

        if (user.getMetroCard() != null) {
            logger.warn("User {} already has a metro card", CardPayload.getUserId());
            throw new MetroCardAlreadyExistsException(CardPayload.getUserId());
        }

        MetroCardEntity metroCard = new MetroCardEntity();
        metroCard.setCardNumber(CardPayload.getCardNumber());
        metroCard.setBalance(CardPayload.getInitialBalance());
        metroCard.setUser(user);

        logger.info("Metro card successfully created for user {}", CardPayload.getUserId());
        return metroCardRepository.save(metroCard);
    }

    // Cancel Metro Pass
//    @Caching(evict = {
//            @CacheEvict(value = "userProfiles", key = "#userId"),
//            @CacheEvict(value = "metroCards", key = "#userId")
//    })
    public String cancelMetroPass(Long userId) {
        logger.info("Cancelling metro pass for user ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userId);
                    return new UserNotFoundException(userId);
                });

        if (user.getMetroCard() == null) {
            logger.warn("No metro card found for user {}", userId);
            throw new MetroCardNotFoundException(userId);
        }

        MetroCardEntity metroCard = user.getMetroCard();
        user.setMetroCard(null);
        userRepository.save(user);
        metroCardRepository.delete(metroCard);

        logger.info("Metro pass canceled successfully for user {}", userId);
        return "Metro pass canceled successfully.";
    }

    // Fetch User Profile
//    @JsonIgnore
//    @Cacheable(value = "userProfiles", key = "#userId")
    public Optional<UserEntity> getUserProfile(Long userId) {
        logger.info("Fetching user profile for user ID: {}", userId);

        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userId);
                    return new UserNotFoundException(userId);
                }));
    }

    @Transactional
//    @Caching(evict = {
//            @CacheEvict(value = "userProfiles", key = "#userId"),
//            @CacheEvict(value = "metroCards", key = "#userId")
//    })
    public String updateMetroCardBalance(Long userId, Double newBalance) {
        logger.info("Updating metro card balance for user ID: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userId);
                    return new UserNotFoundException(userId);
                });

        MetroCardEntity metroCard = user.getMetroCard();
        if (metroCard == null) {
            logger.warn("No metro card found for user {}", userId);
            throw new MetroCardNotFoundException(userId);
        }

        metroCard.setBalance(newBalance);
        metroCardRepository.save(metroCard);

        logger.info("Metro card balance updated successfully for user {}", userId);
        return "Metro card balance updated successfully.";
    }



}
