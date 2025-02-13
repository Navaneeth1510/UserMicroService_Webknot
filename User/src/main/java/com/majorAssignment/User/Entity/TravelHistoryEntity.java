package com.majorAssignment.User.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "travel_history")
public class TravelHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_station", nullable = false)
    private StationEntity sourceStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_station", nullable = false)
    private StationEntity destinationStation;

    private Double fare;
    private LocalDateTime travelTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

    @Override
    public String toString() {
        return "TravelHistoryEntity{" +
                "id=" + id +
                ", sourceStation=" + sourceStation +
                ", destinationStation=" + destinationStation +
                ", fare=" + fare +
                ", travelTime=" + travelTime +
                ", user=" + user +
                '}';
    }
}
