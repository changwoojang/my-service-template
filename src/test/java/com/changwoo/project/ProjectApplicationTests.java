package com.changwoo.project;

import com.changwoo.project.domain.model.Coupon.CouponRepository;
import com.changwoo.project.web.coupon.CouponController;
import com.changwoo.project.web.coupon.CouponService;
import com.changwoo.project.web.coupon.dto.CouponCreate;
import com.changwoo.project.web.coupon.dto.CouponCreateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(CouponController.class)
@SpringBootTest
public class ProjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CouponService couponService;

	@Test
	public void createCoupon() throws Exception {
		CouponCreate couponCreate = new CouponCreate();
		couponCreate.setEmail("changwoo");

//		mockMvc.perform(post("/coupon/create")).andExpect(status().isOk())

//				.andExpect(content().string("hi"))
//				.andDo(print());

		CouponCreateResult couponCreateResult = couponService.create(couponCreate);
		System.out.println(couponCreateResult);

	}

}
