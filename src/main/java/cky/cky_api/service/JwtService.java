//package cky.cky_api.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.lang.module.ResolutionException;
//import java.security.Key;
//import java.util.Date;
//
//@Service
//public class JwtService {
//    private static final Object EMPTY_JWT = null;
//    private static final Object INVALID_JWT = null;
//    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    /*
//    JWT 생성
//    @param userIdx
//    @return String
//     */
//    public String createJwt(Long userIdx) {
//        Date now = new Date();
//
//        return Jwts.builder()
//                .setHeaderParam("type", "jwt")
//                .claim("userIdx", userIdx)
//                .setIssuedAt(now)
//                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24365)))
//                .signWith(key)
//                .compact();
//    }
//
//    /*
//    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
//    @return string
//     */
//    public String getJwt() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        return request.getHeader("Authorization");
//    }
//
//    /*
//    JWT에서 userIdx추출
//    @return int
//    @throws BaseException
//     */
//    public Long getMemberId() throws ResponseException {
//        //1. JWT 추출
//        String accessToken = getJwt();
//        if(accessToken == null || accessToken.length() == 0){
//            throw new ResponseException(EMPTY_JWT);
//        }
//
//        // 2. JWT parsing
//        Jws<Claims> claims;
//        try{
//            claims = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(accessToken);
//        } catch (Exception ignored) {
//            throw new ResponseException(INVALID_JWT);
//        }
//
//        // 3. userIdx 추출
//        return claims.getBody().get("userIdx",Long.class);  // jwt 에서 userIdx를 추출합니다.
//    }
//}
