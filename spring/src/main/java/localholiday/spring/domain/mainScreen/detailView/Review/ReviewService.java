package localholiday.spring.domain.mainScreen.detailView.Review;

import localholiday.spring.domain.bill.BillRepository;
import localholiday.spring.domain.entity.food.FoodBill;
import localholiday.spring.domain.entity.food.FoodReview;
import localholiday.spring.domain.entity.house.HouseBill;
import localholiday.spring.domain.entity.house.HouseReview;
import localholiday.spring.domain.entity.job.JobBill;
import localholiday.spring.domain.entity.tour.TourReview;
import localholiday.spring.domain.entity.tour.TourSpotBill;
import localholiday.spring.domain.food.FoodService;
import localholiday.spring.domain.food.foodBill.FoodBillRepository;
import localholiday.spring.domain.house.HouseService;
import localholiday.spring.domain.house.houseBill.HouseBillRepository;
import localholiday.spring.domain.house.houseReview.HouseReviewRepository;
import localholiday.spring.domain.job.jobBill.JobBillRepository;
import localholiday.spring.domain.tour.TourService;
import localholiday.spring.domain.tour.tourBill.TourBillRepository;
import localholiday.spring.domain.user.UserDTO;
import localholiday.spring.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final FoodService foodService;
    private final HouseService houseService;
    private final TourService tourService;
    private final BillRepository billRepository;
    private final HouseBillRepository houseBillRepository;
    private final HouseReviewRepository houseReviewRepository;
    private final TourBillRepository tourBillRepository;
    private final JobBillRepository jobBillRepository;
    private final FoodBillRepository foodBillRepository;

    public List<String> getBillId(String userId){
        return billRepository.findAllByUserId(userId);
    }
    public String isExist(String userId, String targetId){
        List<String> billIds = getBillId(userId);
        Optional<HouseBill> houseBill;
        Optional<TourSpotBill> tourSpotBill;
        Optional<JobBill> jobBill;
        Optional<FoodBill> foodBill;
        for(String billId : billIds){
            houseBill = houseBillRepository.findByBillIdAndHouseId(billId, targetId);
            tourSpotBill = tourBillRepository.findByBillIdAndTourSpotId(billId,targetId);
            jobBill = jobBillRepository.findByBillIdAndJobId(billId, targetId);
            foodBill = foodBillRepository.findByBillIdAndFoodId(billId, targetId);
            if(houseBill.isPresent())
                return houseBill.get().getBillId();
            else if(tourSpotBill.isPresent())
                return tourSpotBill.get().getBillId();
            else if(jobBill.isPresent())
                return jobBill.get().getBillId();
            else if(foodBill.isPresent())
                return foodBill.get().getBillId();
        }
        return null;
    }

    @Transactional
    public String reviewWrite(String userId, String billId, String targetId, String title, String content, String imgURL, Timestamp start, Timestamp end){
        HouseReview houseReview = HouseReview.builder()
                .billId(billId)
                .content(content)
                .houseId(targetId)
                .userId(userId)
                .title(title)
                .photo(imgURL)
                .startDate(start)
                .endDate(end)
                .created(new Timestamp(System.currentTimeMillis()))
                .build();
        houseReviewRepository.save(houseReview);
        return houseReviewRepository.findByUserIdAndBillIdAndHouseId(userId, billId, targetId).getId();
    }

    public List<ReviewDTO> getReviewList(String uuid){
        List<FoodReview> foodReviews = foodService.getReviews(uuid);
        List<HouseReview> houseReviews = houseService.getReviews(uuid);
        List<TourReview> tourReviews = tourService.getReviews(uuid);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();


        if(!foodReviews.isEmpty()){
            for(FoodReview tmp : foodReviews){
                UserDTO userDTO = userService.getUserDTO(tmp.getUserId());

                ReviewDTO reviewDTO = ReviewDTO.builder()
                        .reviewId(tmp.getId())
                        .reviewContent(tmp.getContent())
                        .reviewCreated(tmp.getCreated())
                        .reviewPhoto(tmp.getPhoto())
                        .nickname(userDTO.getNickname())
                        .userId(userDTO.getUuid())
                        .userPhoto(userDTO.getPhoto())
                        .reviewCreated(tmp.getCreated())
                        .build();

                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        }
        else if(!houseReviews.isEmpty()){
            for(HouseReview tmp : houseReviews){
                UserDTO userDTO = userService.getUserDTO(tmp.getUserId());

                ReviewDTO reviewDTO = ReviewDTO.builder()
                        .reviewId(tmp.getId())
                        .reviewContent(tmp.getContent())
                        .reviewCreated(tmp.getCreated())
                        .reviewPhoto(tmp.getPhoto())
                        .nickname(userDTO.getNickname())
                        .userId(userDTO.getUuid())
                        .userPhoto(userDTO.getPhoto())
                        .reviewCreated(tmp.getCreated())
                        .build();
                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        }
        else if(!tourReviews.isEmpty()){
            for(TourReview tmp : tourReviews){
                UserDTO userDTO = userService.getUserDTO(tmp.getUserId());

                ReviewDTO reviewDTO = ReviewDTO.builder()
                        .reviewId(tmp.getId())
                        .reviewContent(tmp.getContent())
                        .reviewCreated(tmp.getCreated())
                        .reviewPhoto(tmp.getPhoto())
                        .nickname(userDTO.getNickname())
                        .userId(userDTO.getUuid())
                        .userPhoto(userDTO.getPhoto())
                        .reviewCreated(tmp.getCreated())
                        .build();
                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        }
        return null;
    }
}
