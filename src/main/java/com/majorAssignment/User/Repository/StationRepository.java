package com.majorAssignment.User.Repository;

import org.apache.kafka.common.metrics.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import com.majorAssignment.User.Entity.StationEntity;

public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
