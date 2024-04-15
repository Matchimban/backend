package com.project.matchimban.api.restaurant.domain.dto.request;

import com.project.matchimban.api.restaurant.domain.enums.RestaurantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "매장 상태 변경 요청 DTO")
public class RestaurantStatusUpdateRequest {

    @Schema(description = "변경할 매장 상태")
    public RestaurantStatus status;
}
