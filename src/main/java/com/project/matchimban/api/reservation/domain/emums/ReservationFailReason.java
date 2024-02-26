package com.project.matchimban.api.reservation.domain.emums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationFailReason {
    IMPORT_ACCESS_FAIL("import access fail"),
    VERIFY_FAIL("verify fail");

    private final String failReason;

}
