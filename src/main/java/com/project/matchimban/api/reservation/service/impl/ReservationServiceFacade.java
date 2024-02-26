package com.project.matchimban.api.reservation.service.impl;

import com.project.matchimban.api.reservation.domain.dto.ReservationCreatePreRequest;
import com.project.matchimban.common.exception.SVCException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceFacade {

    private final RedissonClient redissonClient;
    private final ReservationServiceImpl reservationService;

    public ResponseEntity createPreReservation(ReservationCreatePreRequest dto, Long userId){
        String key = dto.getRestaurantId() + dto.getRstDate().toString();
        RLock lock = redissonClient.getLock(key);
        try{
            boolean available = lock.tryLock(20, 1, TimeUnit.SECONDS);
            if(!available){
                log.error("락획득실패");
                throw new SVCException("dd");
            }
            return reservationService.createPreReservation(dto, userId);
        } catch (InterruptedException e){
            throw new SVCException(e);
        } finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

}
