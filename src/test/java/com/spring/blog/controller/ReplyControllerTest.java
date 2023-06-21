package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.ReplyInsertDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc // MVC테스트는 브라우저를 켜야 원래 테스트가 가능하므로 브라우저를 대체할 객체를 만들어 수행
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired // 데이터 직렬화에 사용하는 객체
    private ObjectMapper objectMapper;

    // 컨트롤러를 테스트 해야하는데 컨트롤러는 서버에 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지는 않습니다.
    // 각 테스트전에 설정하기
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @DisplayName("2번 글에 대한 전체 댓글을 조회했을때 0번째 요소의 replyWriter는 댓글쓴사람, replyId는 1")
    void findAllRepliesTest() throws Exception { // mockMvc의 예외를 던져줄 Exception
        // given : fixture 설정, 접속 주소 설정
        String replyWriter = "댓글쓴사람";
        long replyId = 1;
        String url = "/reply/2/all";

        // when : 위에 설정한 url로 접속 후 json 데이터 리턴받아 저장하기. ResultActions 형 자료로 json 저장하기
        // get() 메서드의 경우 작성 후 alt + enter 눌러서 mockmvc 관련 요소로 import
        // import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

                                    // fetch(url, {method:'get'}).then(res => res.json()); 에 대응하는 코드
        final ResultActions result = mockMvc.perform(get(url)
                                            .accept(MediaType.APPLICATION_JSON));

        // then : 리턴받은 json 목록의 0번째 요소의 replyWriter와 replyId가 예상과 일치하는지 확인
        // import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
        // import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
        result
                .andExpect(status().isOk()) // 200코드가 출력되었는지 확인
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))// 첫 json의 replyWriter 검사
                .andExpect(jsonPath("$[0].replyId").value(replyId));// 첫 json의 replyId 검사
    }

    @Test
    @Transactional
    @DisplayName("replyId 2번 조회시 얻어진 json객체의 replyWriter는 짹짹이, replyId는 2번")
    public void findByReplyIdTest() throws Exception {
        // given : fixture 세팅 및 요청 주소 세팅
        String replyWriter = "짹짹이";
        long replyId = 2;

        String url = "/reply/2";

        // when : 위에 설정한 url로 접속 후 json 데이터 리턴받아 저장하기. ResultActions 형 자료로 json 저장하기
                                    // fetch(url, {method:'get'}).then(res => res.json()); 에 대응하는 코드
        final ResultActions result = mockMvc.perform(get(url)
                                    .accept(MediaType.APPLICATION_JSON));

        // then : $로만 끝나는 이유는 리턴받은 자료가 리스트가 아니기 때문에, 인덱싱을 할 필요가 없음
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter)) // 단일 json객체는 $생략가능
                .andExpect(jsonPath("$.replyId").value(replyId));
    }

    @Test
    @Transactional
    @DisplayName("blogId 1번에 replyWriter는 또사라짐 replyContent 가기싫다를 넣고 등록 후 전체댓글 조회시 픽스처 일치")
    public void insertReplyTest() throws Exception {
        // given : 픽스처 생성 및  ReplyInsertDTO 객체 생성 후 픽스처주입 + json으로 데이터 직렬화
        long blogId = 1;
        String replyWriter = "또사라짐";
        String replyContent = "가기싫다";
        ReplyInsertDTO replyInsertDTO = new ReplyInsertDTO(blogId, replyWriter, replyContent);
        String url = "/reply";
        String url2 = "/reply/1/all";

        // 데이터 직렬화 : java객체 -> json으로
        final String requestBody = objectMapper.writeValueAsString(replyInsertDTO);

        // when : 직렬화된 데이터를 이용해 post방식으로 url에 요청
        // import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
        mockMvc.perform(post(url) // /reply 주소에 post방식으로 요청을 넣고
                .contentType(MediaType.APPLICATION_JSON)// 전달 자료는 json이며
                .content(requestBody));// 위에서 직렬화한 requestBody 변수를 전달할것이다.

        // then : 위에서 blogId로 지정한 1번글의 전체 데이터를 가져와서,
        //        픽스처와 replyWriter, replyContent가 일치하는지 확인
        final ResultActions result = mockMvc.perform(get(url2)
                                    .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyContent").value(replyContent));
    }
}