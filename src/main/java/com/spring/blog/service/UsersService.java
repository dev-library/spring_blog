package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UsersService {// UserService는 "인증" 만 담당하고, 나머지 회원가입 등은 이 서비스 객체로 처리함.

    private final UserRepository userRepository;
    // 암호화 객체가 필요함(디비에 비밀번호를 암호화해서 넣어야 하기 떄문)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 폼에서 날려준 정보를 디비에 적재하되, 비밀번호는 암호화(인코딩)을 진행한 구문을 인서트함.
    public void save(User user) {

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


}
