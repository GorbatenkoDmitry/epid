package com.epid.epid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//	@EnableCaching
public class EpidApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpidApplication.class, args);
	}

}
