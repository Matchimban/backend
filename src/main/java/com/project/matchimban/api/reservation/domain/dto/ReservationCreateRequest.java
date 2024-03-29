package com.project.matchimban.api.reservation.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.matchimban.api.reservation.domain.emums.ReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.Reservation;
import com.project.matchimban.api.reservation.domain.entity.ReservationMenu;
import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "예약 등록 요청 DTO")
@Data
public class ReservationCreateRequest {

    //import 고유번호
    @NotBlank
    String impUid;
    @NotNull
    Long restaurantId;

    @NotNull
    Long reservationId;

    //예약자
    @NotNull
    Long userId;

    //선택 좌석 크기
    int size;

    //예약날
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate rstDate;
    //예약시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    LocalTime rstTime;

    //정가
    int regularPrice;

    //결제가
    int paymentAmount;

    //메뉴리스트(메뉴이름, 정가, 결제가)
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
                .impUid(impUid)
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
