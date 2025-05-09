package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.yaksha.assignment.controller.AppController;
import com.yaksha.assignment.utils.JavaParserUtils;

@WebMvcTest(AppController.class)
public class ApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testSendPostRequest() throws Exception {
		String requestData = "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}";
		String apiUrl = "https://jsonplaceholder.typicode.com/posts";

		boolean statusOk = false;
		boolean containsName = false;
		boolean containsEmail = false;

		try {
			MvcResult result = mockMvc.perform(post("/sendData")
					.param("apiUrl", apiUrl)
					.contentType("application/json")
					.content(requestData))
					.andReturn(); // Capture the result

			int responseStatus = result.getResponse().getStatus();

			// Check if the HTTP status is OK (200)
			if (responseStatus == 200) {
				statusOk = true;
				String responseBody = result.getResponse().getContentAsString();
				containsName = responseBody.contains("name");
				containsEmail = responseBody.contains("email");
			} else {
				System.out.println("API request failed with status: " + responseStatus);
			}

		} catch (Exception ex) {
			System.out.println("Error occurred: " + ex.getMessage());
		}

		boolean finalResult = statusOk && containsName && containsEmail;

		// Assert test case result while handling failure scenario
		if (!finalResult) {
			System.out.println("Test case failed due to API response issues.");
		}

		yakshaAssert(currentTest(), finalResult ? "true" : "false", businessTestFile);
	}

	@Test
	public void testControllerStructure() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/AppController.java"; // Update path to your
																								// file
		boolean result = JavaParserUtils.checkControllerStructure(filePath, // Pass the class file path
				"RestController", // Check if @RestController is used on the class
				"sendPostRequest", // Check if the method name is correct
				"PostMapping", // Check if @PostMapping is present on the method
				"requestData", // Check if the parameter has @RequestBody annotation
				"String" // Ensure the return type is String
		);
		yakshaAssert(currentTest(), result, businessTestFile);
	}
}
