package com.spring.blog.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

// 쿠키를 모든 로직에 생성하고 파기하는 로직을 만들기 귀찮으니 반복적으로 사용할 코드를 클래스에 모아둔것
// 쿠키에 영구히 박제하는게 아니라, 사용자측에 토큰을 전달하는 매개체로만 쿠키를 사용하고 바로 파기할 목적
public class CookieUtil {
    // 요청값(이름, 값, 만료기간)을 바탕으로 쿠키를 생성하기
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return; // 쿠키가 없으면
        }

        for(Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0); // 쿠키는 유효시간을 0으로 수정하면 남은 시간과 상관없이 자동 파기됨
                response.addCookie(cookie);
            }
        }
    }

    // 객체를 직렬화해서 쿠키로 변환
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    // 쿠키를 역직렬화해서 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue()))
        );
    }

}








