package com.angel.api.practice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.angelbroking.smartapi.SmartConnect;
import com.angelbroking.smartapi.models.User;

@SpringBootTest
class AngelApiApplicationTests {

	@Test
	void contextLoads() {
        SmartConnect connect=new SmartConnect();
        connect.setApiKey("RCvbyZRP");
        User user = connect.generateSession("P401798","1111","278356");
	}

}
