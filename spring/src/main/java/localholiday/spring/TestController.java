package localholiday.spring;

import localholiday.spring.domain.ReverseGeocoding;
import localholiday.spring.global.baseResponse.BaseResponse;
import localholiday.spring.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final S3Uploader s3Uploader;

    @GetMapping("/geo")
    public ResponseEntity<BaseResponse<?>> geo(){
        return ResponseEntity.ok().body(new BaseResponse<>(new ReverseGeocoding().getJSON("1","1")));
    }

    @GetMapping("/road")
    public ResponseEntity<BaseResponse<?>> road(){
        return ResponseEntity.ok().body(new BaseResponse<>(new ReverseGeocoding().getJSON("1","1")));
    }

    @PostMapping(value = "s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<String>> upload(@RequestParam(value = "file")MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(new BaseResponse<>(s3Uploader.upload(file, "test")));
    }
}
