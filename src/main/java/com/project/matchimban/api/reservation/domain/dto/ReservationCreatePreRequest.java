package com.project.matchimban.api.reservation.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ReservationCreatePreRequest {


    @Schema(description = "매장 id")
    @NotNull
    Long restaurantId;

    @Schema(description = "예약할 날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate rstDate;

    @Schema(description = "예약할 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime rstTime;

    @Schema(description = "예약할 좌석")
    @NotNull
    int size;

    @Schema(description = "정가")
    int regularPrice;

    @Schema(description = "결제가")
    int paymentAmount;

    @Schema(description = "예약한 메뉴 리스트")
    List<Menu> menuList = new ArrayList<>();

    @Data
    public static class Menu{
        @Schema(description = "메뉴 pk")
        private Long menuId;
        @Schema(description = "메뉴의 이름")
        private String menuName;
        @Schema(description = "메뉴의 정가")
        private int regularPrice;
        @Schema(description = "메뉴의 결제가")
        private int paymentAmount;
    }


    public Reservation toReservationInitEntity(User user, RestaurantReservation restaurantReservation){
        return Reservation.builder()
                .user(user)
                .restaurantReservation(restaurantReservation)
                .size(size)
                .rstDate(rstDate)
                .rstTime(rstTime)
                .regularPrice(regularPrice)
                .paymentAmount(paymentAmount)
                .status(ReservationStatus.ING)
                .build();
    }

    public List<ReservationMenu> toReservationMenuEntityList(Reservation reservation){
        return this.menuList.stream()
                .map(m -> ReservationMenu.builder()
                        .reservation(reservation)
                        .menuName(m.menuName)
                        .regularPrice(m.regularPrice)
                        .paymentAmount(m.paymentAmount)
                        .build()
                ).collect(Collectors.toList());
    }

}
