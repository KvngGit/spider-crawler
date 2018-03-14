package org.coin.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpiderCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderCrawlerApplication.class, args);
	}
}
