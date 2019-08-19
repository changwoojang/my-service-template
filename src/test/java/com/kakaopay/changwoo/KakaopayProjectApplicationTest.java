package com.kakaopay.changwoo;

import com.kakaopay.changwoo.util.SecurityUtil;
import com.kakaopay.changwoo.web.jwt.JwtService;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class KakaopayProjectApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webContext;

	@Autowired
	private JwtService jwtService;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext)
				.alwaysDo(MockMvcResultHandlers.print())
				.build();
	}

	@Test
	public void 계정_생성() throws Exception{

		//GIVEN
		String memberId = "changwooJang";
		String password = "1q2w3e4r!@";

		//WHEN
		ResultActions resultActions = mockMvc.perform(post("/v1/bank/member/signup")
				.param("memberId",memberId)
				.param("password",password))
				.andDo(print());

		//THEN
		resultActions
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.memberId").value("changwooJang"))
				.andExpect(jsonPath("$.token").value(IsNull.notNullValue()));

	}

	@Test
	public void 계정_로그인() throws Exception{

		//GIVEN
		String memberId = "changwoo";
		String password = "1q2w3e4r!@";

		mockMvc.perform(post("/v1/bank/member/signup")
				.param("memberId",memberId)
				.param("password",password))
				.andDo(print());

		String encryptedPassword = SecurityUtil.encrypt(password);

		//THEN
		ResultActions resultActions = mockMvc.perform(post("/v1/bank/member/login")
				.param("memberId",memberId)
				.param("password",encryptedPassword))
				.andDo(print());

		//WHEN
		resultActions
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.memberId").value("changwoo"))
				.andExpect(jsonPath("$.token").value(IsNull.notNullValue()));
	}

	@Test
	public void refresh_토큰_재발급() throws Exception{

		//GIVEN
		String expired_token = "eyJ0eXBlIjoiSldUIiwicmVnRGF0ZSI6MTU2NjIyNzU1NTcwMCwiYWxnIjoiSFMyNTYifQ.eyJtZW1iZXJJZCI6ImNoYW5nd29vIn0.XtfczRDAx3Z-J7OqWJ09Lx9a4B7IVbcG6h3ge9vZxx8";

		//WHEN
		ResultActions resultActions = mockMvc.perform(get("/v1/bank/member/token-refresh")
				.header("Authorization", "Bearer " + expired_token)
				.param("memberId","changwoo")
				.param("password","123123123"))
				.andDo(print());

		//THEN
		resultActions
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.memberId").value("changwoo"))
				.andExpect(jsonPath("$.token").value(IsNull.notNullValue()));
	}

	@Test
	public void 전체_년도에서_은행의_지원금액_평균_중에서_가장_작은_금액과_큰_금액을_출력() throws Exception {

		//GIVEN
		String decodeFileName = URLDecoder.decode(getClass().getClassLoader().getResource("서버개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv").getFile(),"UTF-8");
		String content = new String(Files.readAllBytes(Paths.get(decodeFileName)),Charset.forName("EUC-KR"));
		MockMultipartFile multipartFile = new MockMultipartFile("file", decodeFileName,
				"text/csv", content.getBytes("EUC-KR"));

		String token = jwtService.create("memberId","changwoo");

		this.mockMvc.perform(multipart("/v1/file/upload")
				.file(multipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", "Bearer " + token))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fileType").value("text/csv"))
				.andExpect(jsonPath("$.fileRecordCount").value(154));

		//WHEN
		ResultActions resultActions = mockMvc.perform(get("/v1/bank/supply-amount/average")
				.header("Authorization", "Bearer " + token)
				.param("bankName","KEB_BANK"))
				.andDo(print());

		//THEN
		resultActions
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bank").value("외환은행"))
				.andExpect(jsonPath("$.support_amount",hasSize(2)));
	}
	@Test
	public void 모든_은행의_목록() throws Exception {

		//GIVEN
		String decodeFileName = URLDecoder.decode(getClass().getClassLoader().getResource("서버개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv").getFile(),"UTF-8");
		String content = new String(Files.readAllBytes(Paths.get(decodeFileName)),Charset.forName("EUC-KR"));
		MockMultipartFile multipartFile = new MockMultipartFile("file", decodeFileName,
				"text/csv", content.getBytes("EUC-KR"));

		String token = jwtService.create("memberId","changwoo");

		this.mockMvc.perform(multipart("/v1/file/upload")
				.file(multipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", "Bearer " + token))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fileType").value("text/csv"))
				.andExpect(jsonPath("$.fileRecordCount").value(154));

		String expectedResult = "{\"name\":\"주택금융 공급 금융기관(은행) 목록\",\"" +
				"data\":[{\"institute_name\":\"국민은행\",\"institute_code\":\"bnk1111\"}," +
				"{\"institute_name\":\"우리은행\",\"institute_code\":\"bnk2222\"}," +
				"{\"institute_name\":\"신한은행\",\"institute_code\":\"bnk3333\"}," +
				"{\"institute_name\":\"한국시티은행\",\"institute_code\":\"bnk4444\"}," +
				"{\"institute_name\":\"하나은행\",\"institute_code\":\"bnk5555\"}," +
				"{\"institute_name\":\"농협은행/수협은행\",\"institute_code\":\"bnk6666\"}," +
				"{\"institute_name\":\"외환은행\",\"institute_code\":\"bnk7777\"},{" +
				"\"institute_name\":\"기타은행\",\"institute_code\":\"bnk8888\"}]}";

		//WHEN
		ResultActions resultActions=mockMvc.perform(get("/v1/bank/list")
				.header("Authorization", "Bearer " + token))
				.andDo(print());


		//THEN
		resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json(expectedResult))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("주택금융 공급 금융기관(은행) 목록"))
				.andExpect(jsonPath("$.data",hasSize(8)));
	}


	@Test
	public void 파일_업로드() throws Exception {

		//GIVEN
		String decodeFileName = URLDecoder.decode(getClass().getClassLoader().getResource("서버개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv").getFile(),"UTF-8");
		String content = new String(Files.readAllBytes(Paths.get(decodeFileName)),Charset.forName("EUC-KR"));
		MockMultipartFile multipartFile = new MockMultipartFile("file", decodeFileName,
				"text/csv", content.getBytes("EUC-KR"));
		String token = jwtService.create("memberId","changwoo");

		//WHEN
		ResultActions resultActions = mockMvc.perform(multipart("/v1/file/upload")
				.file(multipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", "Bearer " + token))
				.andDo(print());

		//THEN
		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("@.fileType").value("text/csv"))
				.andExpect(jsonPath("@.fileRecordCount").value(154));

	}

	@Test
	public void 년도별_각_금융기관의_지원금액_합계() throws Exception {

		//GIVEN
		String decodeFileName = URLDecoder.decode(getClass().getClassLoader().getResource("서버개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv").getFile(),"UTF-8");
		String content = new String(Files.readAllBytes(Paths.get(decodeFileName)),Charset.forName("EUC-KR"));
		MockMultipartFile multipartFile = new MockMultipartFile("file", decodeFileName,
				"text/csv", content.getBytes("EUC-KR"));
		String token = jwtService.create("memberId","changwoo");

		this.mockMvc.perform(multipart("/v1/file/upload")
				.file(multipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", "Bearer " + token))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fileType").value("text/csv"))
				.andExpect(jsonPath("$.fileRecordCount").value(154));

		//WHEN
		ResultActions resultActions = mockMvc.perform(get("/v1/bank/supply-amount/total")
				.header("Authorization", "Bearer " + token)
				.param("period","year"))
				.andDo(print());

		//THEN
		resultActions
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"));
	}

	@Test
	public void 년도별_각_금융기관의_지원금액_가장_큰_기관() throws Exception {

		//GIVEN
		String decodeFileName = URLDecoder.decode(getClass().getClassLoader().getResource("서버개발_사전과제3_주택금융신용보증_금융기관별_공급현황.csv").getFile(),"UTF-8");
		String content = new String(Files.readAllBytes(Paths.get(decodeFileName)),Charset.forName("EUC-KR"));
		MockMultipartFile multipartFile = new MockMultipartFile("file", decodeFileName,
				"text/csv", content.getBytes("EUC-KR"));
		String token = jwtService.create("memberId","changwoo");

		this.mockMvc.perform(multipart("/v1/file/upload")
				.file(multipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", "Bearer " + token))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fileType").value("text/csv"))
				.andExpect(jsonPath("$.fileRecordCount").value(154));

		String expectedResult = "{\"year\":\"2016\",\"bank\":\"기타은행\"}";

		//WHEN
		ResultActions resultActions = mockMvc.perform(get("/v1/bank/supply-amount/top")
				.header("Authorization", "Bearer " + token))
				.andDo(print());

		//THEN
		resultActions
				.andExpect(content().string(expectedResult))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.year").value(2016))
				.andExpect(jsonPath("$.bank").value("기타은행"));
	}
}
