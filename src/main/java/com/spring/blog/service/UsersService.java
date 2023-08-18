package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UsersService {// UserService는 "인증" 만 담당하고, 나머지 회원가입 등은 이 서비스 객체로 처리함.

    private final UserRepository userRepository;
    // 암호화 객체가 필요함(디비에 비밀번호를 암호화해서 넣어야 하기 떄문)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository,
                        @Lazy BCryptPasswordEncoder bCryptPasswordEncoder){ // 지연 주입
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 폼에서 날려준 정보를 디비에 적재하되, 비밀번호는 암호화(인코딩)을 진행한 구문을 인서트함.
    public void save(User user) {
         //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
         User newUser = User.builder()
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();

        userRepository.save(newUser);
    }

    // 아이디를 집어넣으면, 해당 계정 전체 정보를 얻어올 수 있는 메서드 작성(컨트롤러에서 호출 가능)
    public User getByCredentials(String loginId){
        return userRepository.findByLoginId(loginId);
    }

    // 회원가입이 되었는지 안 되었는지 체킹하기 위해서 조회하는 구문 추가
    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    // 소셜로그인은 이메일 기반 로그인이 되므로 이메일로도 조회
    public User findByEmail(String email) {
        // 유저레포지토리에 쿼리메서드 형식으로 이메일 조회 추가.
        return userRepository.findByEmail(email);
    }




}





