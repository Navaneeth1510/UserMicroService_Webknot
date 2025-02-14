package com.majorAssignment.User.Service;

import com.majorAssignment.User.Dto.TravelHistoryDto;
import com.majorAssignment.User.Entity.TravelHistoryEntity;
import com.majorAssignment.User.Exceptions.TravelHistoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TravelHistoryService {

    private final RestTemplate restTemplate;

    public TravelHistoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<List<TravelHistoryEntity>> getTravelHistory(Long userId) {
        String travelHistoryUrl = "http://localhost:8082/api/v1/metro/get-travel-history/" + userId;
        try {
            TravelHistoryEntity[] travelHistory = restTemplate.getForObject(travelHistoryUrl, TravelHistoryEntity[].class);
            return CompletableFuture.completedFuture(travelHistory != null ? Arrays.asList(travelHistory) : List.of());
        } catch (Exception e) {
            throw new TravelHistoryException("Failed to retrieve travel history for user ID: " + userId, e);
        }
    }
}

