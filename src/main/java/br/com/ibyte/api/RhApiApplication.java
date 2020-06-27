package br.com.ibyte.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class RhApiApplication {
	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder
				.baseUrl("https://5e61af346f5c7900149bc5b3.mockapi.io/desafio03")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(RhApiApplication.class, args);
	}

}
