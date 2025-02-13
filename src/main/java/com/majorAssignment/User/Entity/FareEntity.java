package com.majorAssignment.User.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fares")
public class FareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_station", nullable = false)
    private StationEntity fromStation;

    @ManyToOne
    @JoinColumn(name = "to_station", nullable = false)
    private StationEntity toStation;

    @Column(nullable = false)
    private Double fare;

    @Override
    public String toString() {
        return "FareEntity{" +
                "id=" + id +
                ", fromStation=" + (fromStation != null ? fromStation.getName() : "null") +
                ", toStation=" + (toStation != null ? toStation.getName() : "null") +
                ", fare=" + fare +
                '}';
    }

}