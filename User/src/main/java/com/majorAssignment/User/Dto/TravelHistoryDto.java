package com.majorAssignment.User.Dto;

import java.time.LocalDateTime;

public class TravelHistoryDto {
    Long id;
    StationDto sourceStation;
    StationDto destinationStation;
    Double fare;
    LocalDateTime time;
}
