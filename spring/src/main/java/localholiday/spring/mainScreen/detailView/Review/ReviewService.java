package localholiday.spring.mainScreen.detailView.Review;

import localholiday.spring.entity.food.FoodReview;
import localholiday.spring.entity.house.HouseReview;
import localholiday.spring.entity.tour.TourReview;
import localholiday.spring.food.FoodService;
import localholiday.spring.house.HouseService;
import localholiday.spring.tour.TourService;
import localholiday.spring.user.UserDTO;
import localholiday.spring.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final FoodService foodService;
    private final HouseService houseService;
    private final TourService tourService;

    public List<ReviewDTO> getReviewList(String uuid){
        List<FoodReview> foodReviews = foodService.getReviews(uuid);
        List<HouseReview> houseReviews = houseService.getReviews(uuid);
        List<TourReview> tourReviews = tourService.getReviews(uuid);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();


        if(!foodReviews.isEmpty()){
            for(FoodReview tmp : foodReviews){
                ReviewDTO reviewDTO = new ReviewDTO();
                UserDTO userDTO = new UserDTO();
                userDTO = userService.getUserDTO(tmp.getUserId());
                reviewDTO.setReviewId(tmp.getId());
                reviewDTO.setReviewPhoto(tmp.getPhoto());
                reviewDTO.setReviewContent(tmp.getContent());
                reviewDTO.setReviewCreated(tmp.getCreated());
                reviewDTO.setNickname(userDTO.getNickname());
                reviewDTO.setUserId(userDTO.getUuid());
                reviewDTO.setUserPhoto(userDTO.getPhoto());
                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        }
        else if(!houseReviews.isEmpty()){
            for(HouseReview tmp : houseReviews){
                ReviewDTO reviewDTO = new ReviewDTO();
                UserDTO userDTO = new UserDTO();
                userDTO = userService.getUserDTO(tmp.getUserId());
                reviewDTO.setReviewId(tmp.getId());
                reviewDTO.setReviewPhoto(tmp.getPhoto());
                reviewDTO.setReviewContent(tmp.getContent());
                reviewDTO.setReviewCreated(tmp.getCreated());
                reviewDTO.setNickname(userDTO.getNickname());
                reviewDTO.setUserId(userDTO.getUuid());
                reviewDTO.setUserPhoto(userDTO.getPhoto());
                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        }
        else if(!tourReviews.isEmpty()){
            for(TourReview tmp : tourReviews){
                ReviewDTO reviewDTO = new ReviewDTO();
                UserDTO userDTO = new UserDTO();
                userDTO = userService.getUserDTO(tmp.getUserId());
                reviewDTO.setReviewId(tmp.getId());
                reviewDTO.setReviewPhoto(tmp.getPhoto());
                reviewDTO.setReviewContent(tmp.getContent());
                reviewDTO.setReviewCreated(tmp.getCreated());
                reviewDTO.setNickname(userDTO.getNickname());
                reviewDTO.setUserId(userDTO.getUuid());
                reviewDTO.setUserPhoto(userDTO.getPhoto());
                reviewDTOS.add(reviewDTO);
            }
            return reviewDTOS;
        }
        return null;
    }
}
