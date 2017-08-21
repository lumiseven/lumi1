package com.seven.rabbitmqhello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.seven.rabbitmqhello.sender.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqHelloApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private Sender sender;
	
	@Test
	public void hello() throws Exception {
		sender.send();
	}
}
