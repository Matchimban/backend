package com.project.matchimban.api.restaurant.controller;

import com.project.matchimban.api.auth.security.model.CurrentUser;
import com.project.matchimban.api.auth.security.model.CustomUserDetails;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantCreateRequest;
import com.project.matchimban.api.restaurant.domain.dto.request.RestaurantUpdateRequest;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantReadResponse;
import com.project.matchimban.api.restaurant.domain.dto.response.RestaurantsReadResponse;
import com.project.matchimban.api.restaurant.service.RestaurantService;
import com.project.matchimban.common.exception.ValidResult;
import com.project.matchimban.common.response.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Restaurant", description = "매장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Operation(summary = "매장 등록", description = "매장 정보와 매장 이미지 정보를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "등록 성공"),
            @ApiResponse(responseCode = "40001", description = "입력값 유효성 검증 실패", content = @Content(schema = @Schema(implementation = ValidResult.class))),
            @ApiResponse(responseCode = "60000", description = "이미 존재하는 회원"),
            @ApiResponse(responseCode = "63000", description = "저장 안 된 파일"),
            @ApiResponse(responseCode = "63001", description = "읽을 수 없는 파일의 확장자")
    })
    @PreAuthorize("hasAnyRole('OWNER', 'USER')")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> registerRestaurant(
            @Parameter(description = "매장의 정보와 이미지 리스트를 받는 객체")
            @ModelAttribute @Valid RestaurantCreateRequest request,
            @Parameter(description = "로그인 정보를 가져오는 객체")
            @CurrentUser CustomUserDetails userDetails
    ) {
        restaurantService.registerRestaurant(request, userDetails);
        return new ResponseEntity<>(new ResultData(), HttpStatus.CREATED);
    }

    @Operation(summary = "매장 전체 조회", description = "매장 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantsReadResponse.class))))
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getRestaurants() {
        ResultData result = new ResultData();
        result.setResult(restaurantService.getRestaurants());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "매장 상세 조회", description = "매장을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "조회 성공", content = @Content(schema = @Schema(implementation = RestaurantReadResponse.class)))
    })
    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity<Object> getRestaurantById(
            @Parameter(description = "매장 id 값을 받아옵니다.")
            @PathVariable Long restaurantId
    ) {
        ResultData result = new ResultData();
        result.setResult(restaurantService.getRestaurant(restaurantId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "매장 수정", description = "매장을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20000", description = "수정 성공")
    })
    //@PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PatchMapping(value = "/{restaurantId}")
    public ResponseEntity<Object> updateRestaurant(
            @Parameter(description = "매장 id 값을 받아옵니다.")
            @PathVariable Long restaurantId,
            @Parameter(description = "매장 수정 내용을 받아옵니다.")
            @RequestBody RestaurantUpdateRequest dto
    ) {
        restaurantService.updateRestaurant(restaurantId, dto);
        return new ResponseEntity<>(new ResultData(), HttpStatus.OK);
    }
}
