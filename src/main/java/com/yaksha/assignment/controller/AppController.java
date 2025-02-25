package com.yaksha.assignment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AppController {

	private final RestTemplate restTemplate;

	public AppController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@PostMapping("/sendData")
	public String sendPostRequest(@RequestParam String apiUrl, @RequestBody String requestData) {
		// Sending the POST request to the external API (jsonplaceholder)
		String response = restTemplate.postForObject(apiUrl, requestData, String.class);

		// Return the response from the external API, along with the sent data
		return "Data received: " + requestData + "\nResponse from API: " + response;
	}
}
