package localholiday.spring.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;

@Service
public class JwtService {

    public static final String secretKey ="4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX";

    public String getSubject(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJwt(token)
                .getBody();

        return claims.getSubject();
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
