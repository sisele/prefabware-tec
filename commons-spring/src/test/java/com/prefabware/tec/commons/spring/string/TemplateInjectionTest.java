package com.prefabware.tec.commons.spring.string;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by stefan on 21.12.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TemplateInjectionTest.Config.class})
@TestPropertySource(properties = {"template=my template with <placeholder>"})
public class TemplateInjectionTest {
	@Component
	static class Holder {
		@Value("${template}")
		Template template;
	}

	@Configuration
	static class Config {

		@Bean
		Holder holder() {
			return new Holder();
		}
	}

	@Autowired
	Holder holder;

	@Test
	public void test() {
		String replaced = holder.template.expand("placeholder", "value");
		assertEquals("my template with value",replaced);
	}
}
