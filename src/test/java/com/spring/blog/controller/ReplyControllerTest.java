package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.repository.ReplyRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    // 임시적으로 ReplyRepository를 생성
    // 레포지토리 레이어의 메서드는 쿼리문을 하나만 호출하는것이 보장되지만
    // 서비스 레이어의 메서드는 추후에 쿼리문을 두 개 이상 호출할수도 있고, 그런 변경이 생겼을때 테스트코드도 같이 수정할 가능성이 생김
    @Autowired
    private ReplyRepository replyRepository;


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
        // given : 픽스처 생성 및  ReplyCreateRequestDTO 객체 생성 후 픽스처주입 + json으로 데이터 직렬화
        long blogId = 1;
        String replyWriter = "또사라짐";
        String replyContent = "가기싫다";
        ReplyCreateRequestDTO replyInsertDTO = new ReplyCreateRequestDTO(blogId, replyWriter, replyContent);
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

    @Test
    @Transactional
    @DisplayName("댓글번호 3번을 삭제할 경우, 글번호 2번의 댓글수는 3개, 그리고 단일댓글 조회시 null")
    public void deleteReplyTest() throws Exception {
        // given : 픽스처 세팅, 요청주소까지
        long replyId = 3;
        long blogId = 2;
        String url = "http://localhost:8080/reply/3";

        // when : 삭제 수행
        // .accept() 는 리턴 데이터가 있는 경우에 해당 데이터를 어떤 형식으로 받아올지 기술
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));


        // then : repository를 이용해 전체 데이터를 가져온 후, 개수 비교 및 삭제한 3번댓글은 null이 리턴되는지 확인
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        assertEquals(3, resultList.size());
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
        assertNull(result);
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 4번의 replyWriter를 냥냥이로, replyContent를 왈왈 로 바꾼뒤 조회시 픽스처 일치")
    public void updateReplyTest() throws Exception{
        // given : 픽스처 생성 및 ReplyUpdateRequestDTO 객체 생성 후 픽스처 주입 + json으로 데이터 직렬화
        String replyWriter = "냥냥이";
        String replyContent = "왈왈";
        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                                                                            .replyWriter(replyWriter)
                                                                            .replyContent(replyContent)
                                                                            .build();

        String url = "/reply/4"; // 4번댓글에 대한 수정 요청 넣기

        // 데이터 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyUpdateRequestDTO);

        // when : 직렬화된 데이터를 이용해 patch방식으로 세팅된 요청 주소에 요청 넣기
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)// 보내는 데이터는 JSON
                .content(requestBody));// 직렬화된 데이터 전송

        // then : 위에서 수정한 4번 댓글에 대한 정보를 가져와서 픽스처와 비교
        final ResultActions result = mockMvc.perform(get(url)
                                                    .accept(MediaType.APPLICATION_JSON));

        // 얻어온 4번 코드의 요청 결과는 200이고, replyWriter, replyContent는 픽스처와 일치한다.
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                .andExpect(jsonPath("$.replyContent").value(replyContent));
    }
}