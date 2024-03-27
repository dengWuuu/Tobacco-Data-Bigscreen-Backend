package cn.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.com.v2.mapper")
public class GogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GogoApplication.class, args);
	}

}
