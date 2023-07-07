package localholiday.spring.domain.mainScreen.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import localholiday.spring.domain.bill.BillService;
import localholiday.spring.domain.food.FoodService;
import localholiday.spring.domain.food.foodBill.FoodBillService;
import localholiday.spring.domain.house.HouseService;
import localholiday.spring.domain.house.houseBill.HouseBillService;
import localholiday.spring.domain.job.JobService;
import localholiday.spring.domain.job.jobBill.JobBillService;
import localholiday.spring.domain.tour.TourService;
import localholiday.spring.domain.tour.tourBill.TourBillService;
import localholiday.spring.global.baseResponse.BaseResponse;
import localholiday.spring.global.baseResponse.BaseResponseStatus;
import localholiday.spring.global.jwt.JwtService;
import localholiday.spring.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final BillService billService;
    private final JobBillService jobBillService;
    private final TourBillService tourBillService;
    private final FoodBillService foodBillService;
    private final HouseBillService houseBillService;
    private final JobService jobService;
    private final TourService tourService;
    private final FoodService foodService;
    private final HouseService houseService;
    private final JwtService jwtService;
    private final RedisService redisService;

    @PostMapping("/reservation")
    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "예약하기", description = "선택한 알바/맛집/관광지/숙소의 UUID들을 제공해줘야함")
    public ResponseEntity<BaseResponse<?>> reservation(@RequestHeader(value = "Authorization") String token, @RequestBody ReservationDTO reservationDTO){
        String uid = jwtService.parseJwtToken(token, "uid");

        if(!redisService.isExists(uid+":app:access"))
            return ResponseEntity.badRequest().body(new BaseResponse<>("로그인 중인 유저가 아닙니다.", BaseResponseStatus.BAD_REQUEST));
        else{
            String billId = billService.reservation(uid,reservationDTO.getStart(), reservationDTO.getEnd(), reservationDTO.getLocation());
            for(String uuid : reservationDTO.getUuid()){
                log.info("uuid={}", uuid);
                if(foodService.hasUUID(uuid).isPresent())
                    foodBillService.makeBill(billId, uuid);
                else if(houseService.hasUUID(uuid).isPresent())
                    houseBillService.makeBill(billId, uuid);
                else if(jobService.hasUUID(uuid).isPresent())
                    jobBillService.makeBill(billId, uuid);
                else if(tourService.hasUUID(uuid).isPresent())
                    tourBillService.makeBill(billId, uuid);
            }
            return ResponseEntity.ok().body(new BaseResponse<>(billId));
        }

    }

}
