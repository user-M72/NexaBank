package BankApp.SpringBank.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieService {

    private static final String REFRESH_TOKEN_COOKIE = "refreshToken";
    private static final String REFRESH_PATH = "/api/auth/v1/refresh";
    private static final int SEVEN_DAYS = 7 * 24 * 60 * 60;


    public void setRefreshTokenCookie(HttpServletResponse response, String token){

        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, token)
                .httpOnly(true)
                .secure(false)
                .path(REFRESH_PATH)
                .maxAge(SEVEN_DAYS)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearRefreshTokenCookie(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, "")
                .httpOnly(true)
                .secure(true)
                .path(REFRESH_PATH)
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
