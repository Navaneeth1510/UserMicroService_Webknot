package com.majorAssignment.User.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "metro_cards")
public class MetroCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private Double balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

    @Override
    public String toString() {
        return "MetroCardEntity{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", balance=" + balance +
                ", user=" + user +
                '}';
    }
}

