package localholiday.spring.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class JwtService {

    public static final String secretKey = System.getenv("JWT_KEY");

    public String getSubject(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJwt(token)
                .getBody();

        return claims.getSubject();
    }

    public String parseJwtToken(String tokenHeader, String customClaim){
        validationTokenHeader(tokenHeader);
        String token = extractToken(tokenHeader);
        String[] payload = token.split("\\.");
        token = token.replace("+", "-");
        token = token.replace("/", "_");
        log.info("token={}",token);
        log.info("payload={}", payload[1]);
        byte[] deco = Base64.getDecoder().decode(payload[1]);
        String decodePayload = new String(deco, StandardCharsets.UTF_8);
        log.info("decode={}", decodePayload);

        ObjectMapper mapper = new ObjectMapper();
        try{
            JsonNode node = mapper.readTree(decodePayload);
            log.info("customClaim={}", node.get(customClaim).toString());
            return node.get(customClaim).toString().replaceAll("\"", "");
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;

    }

    private String extractToken(String tokenHeader) {
        return tokenHeader.substring("Bearer ".length());
    }

    private void validationTokenHeader(String tokenHeader) {
        if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("잘못된 헤더입니다.");
        }
    }

    public String getCustomValue(String token, String customClaim){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJwt(token)
                .getBody();

        return  claims.get(customClaim).toString();
    }
}
