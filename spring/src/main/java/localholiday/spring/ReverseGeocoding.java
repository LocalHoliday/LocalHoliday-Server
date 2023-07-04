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

import java.util.List;

@Slf4j
@Service
public class ReverseGeocoding {

    public JsonNode getJSON(String lat, String lon){
        try{
            String apiURL = "https://dapi.kakao.com/v2/local/geo/coord2address.json?";
            String auth = System.getenv("KAKAO_API_KEY");
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(apiURL + "x="+lon+"&y="+lat);
            getRequest.setHeader("Authorization","KakaoAK "+auth);
            log.debug("getRequest={}", getRequest);
            log.debug("header={}",getRequest.getHeaders("Authorization"));
            HttpResponse response = client.execute(getRequest);
            if(response.getStatusLine().getStatusCode() == 200){
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode bodyJson = mapper.readTree(body);
                return bodyJson;
            } else {
                log.info("error={}", response.getStatusLine().getStatusCode());
                return null;
            }

        }catch (Exception e){
            log.info("error: {}", e.toString());
            return null;
        }
    }

    public List<JsonNode> getAddr(String lat, String lon){
        JsonNode body = getJSON(lat, lon);
        if(!body.isNull()){
            return body.findValues("address_name");
        }
        return null;
    }
}
