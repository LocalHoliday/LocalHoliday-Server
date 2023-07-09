package localholiday.spring.domain.mainScreen.detailView.Review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@Schema(description = "리뷰DTO")
public class ReviewDTO {
    @Schema(description = "유저UUID")
    private String userId;
    @Schema(description = "유저 닉네임")
    private String nickname;
    @Schema(description = "유저 프로필 사진URL")
    private String userPhoto;
    @Schema(description = "리뷰UUID")
    private String reviewId;
    @Schema(description = "리뷰사진 URL")
    private String reviewPhoto;
    @Schema(description = "리뷰 내용")
    private String reviewContent;
    @Schema(description = "리뷰생성시간", example = "2023-01-01 00:00:00.0000")
    private Timestamp reviewCreated;


}
