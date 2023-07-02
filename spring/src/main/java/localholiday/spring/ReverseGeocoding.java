package localholiday.spring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReverseGeocoding {

    public JsonNode get(String lat, String lon){
        try{
            String apiURL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(apiURL + "?x=126.6244837546&y=37.1686580243");
            getRequest.setHeader("Authorization","KakaoAK 3a92293484e6b80d6b4910b9937c7a31");
            log.info("getRequest={}", getRequest);
            log.info("header={}",getRequest.getHeaders("Authorization"));
            HttpResponse response = client.execute(getRequest);
            if(response.getStatusLine().getStatusCode() == 200){
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode bodyJson = mapper.readTree(body);
                log.info("json={}", bodyJson);
                return bodyJson;
            } else {
                log.info("error={}", response.getStatusLine().getStatusCode());
            }

        }catch (Exception e){
            log.info("error: {}", e.toString());
            return null;
        }
        return null;
    }
}
