package com.majorAssignment.User.Repository;

import com.majorAssignment.User.Entity.TravelHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelHistoryRepository extends JpaRepository<TravelHistoryEntity, Long> {
    List<TravelHistoryEntity> findTop10ByUserIdOrderByTravelTimeDesc(Long userId);
}
