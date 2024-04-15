package com.project.matchimban;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreatePreRequest;
import com.project.matchimban.api.reservation.domain.emums.RestaurantReservationStatus;
import com.project.matchimban.api.reservation.domain.entity.ReservationSeat;
import com.project.matchimban.api.reservation.domain.entity.ReservationTime;
import com.project.matchimban.api.reservation.domain.entity.RestaurantReservation;
import com.project.matchimban.api.reservation.repository.ReservationRepository;
import com.project.matchimban.api.reservation.repository.ReservationSeatRepository;
import com.project.matchimban.api.reservation.repository.ReservationTimeRepository;
import com.project.matchimban.api.reservation.repository.RestaurantReservationRepository;
import com.project.matchimban.api.reservation.service.impl.ReservationServiceImpl;
import com.project.matchimban.api.restaurant.domain.entity.Restaurant;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantCategory;
import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import com.project.matchimban.api.restaurant.repository.RestaurantRepository;
import com.project.matchimban.api.user.domain.entity.User;
import com.project.matchimban.api.user.domain.enums.UserRole;
import com.project.matchimban.api.user.domain.enums.UserStatus;
import com.project.matchimban.api.user.repository.UserRepository;
import com.project.matchimban.common.global.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationTest {

    @Autowired
    private ReservationServiceImpl reservationService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantReservationRepository restaurantReservationRepository;
    @Autowired
    private ReservationSeatRepository reservationSeatRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;
    @Autowired
    private UserRepository userRepository;

    String tmpStr="test";
    Long userId= 2L;
    Long restaurantId = 1L;
    LocalDate rstDate = LocalDate.parse("2024-12-01");
    LocalTime rstTime = LocalTime.parse("14:00:00");
    int size = 4;
    int limit_cnt= 15;
    int numberOfThreads = 50;


    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email(tmpStr)
                .password(tmpStr)
                .name(tmpStr)
                .nickname(tmpStr)
                .phone(tmpStr)
                .userRole(UserRole.ROLE_USER)
                .status(UserStatus.ACTIVE)
                .platformType(tmpStr)
                .refreshToken(tmpStr)
                .build();
        userRepository.save(user);
        userId = user.getId();

        Restaurant restaurant = Restaurant.builder()
                .user(user)
                .name(tmpStr)
                .businessNumber(tmpStr)
                .originCountry(tmpStr)
                .category(RestaurantCategory.KOREA)
                .status(RestaurantStatus.PUBLISHED)
                .address(Address.createAddress(tmpStr,tmpStr,tmpStr,tmpStr, 11.1d,11.1d))
                .build();
        restaurantRepository.save(restaurant);
        restaurantId = restaurant.getId();

        RestaurantReservation rstRsv = RestaurantReservation.createRestaurantReservation(restaurant, RestaurantReservationStatus.ACTIVE);
        restaurantReservationRepository.save(rstRsv);

        reservationSeatRepository.save(ReservationSeat.createReservationSeat(rstRsv, size, limit_cnt));
        reservationTimeRepository.save(ReservationTime.createReservationTime(rstRsv, rstTime));
    }


    @Test
    void 예약_동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        ReservationCreatePreRequest dto = ReservationCreatePreRequest.builder()
                .restaurantId(restaurantId)
                .rstDate(rstDate)
                .rstTime(rstTime)
                .size(size)
                .regularPrice(0)
                .paymentAmount(0)
                .build();


        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.createPreReservation(dto, userId);
                    System.out.println(latch.getCount());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();


        assertThat(reservationRepository.findAll().size()).isEqualTo(limit_cnt);
    }
}
