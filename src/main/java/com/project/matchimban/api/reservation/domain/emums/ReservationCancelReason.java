package com.project.matchimban.api.reservation.domain.emums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationCancelReason {

    CUSTOMER("customer's cancel"),
    OWNER("owner's cancel");

    private final String cancelReason;

}
