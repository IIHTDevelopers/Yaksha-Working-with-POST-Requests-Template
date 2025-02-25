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
		// Prepare test data (JSON payload)
		String requestData = "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}";

		// Define the external URL for the POST request (e.g., jsonplaceholder API)
		String apiUrl = "https://jsonplaceholder.typicode.com/posts"; // The URL to send the POST request to

		// Declare boolean flags to track each assertion result
		boolean statusOk = false;
		boolean containsName = false;
		boolean containsEmail = false;

		try {
			// Perform the POST request with the test data to the external API via internal
			// controller
			MvcResult result = mockMvc.perform(post("/sendData") // This is your internal controller's endpoint
					.param("apiUrl", apiUrl) // Pass the external API URL as a parameter
					.contentType("application/json") // Set the content type as JSON
					.content(requestData)) // Provide the request data as the body
					.andExpect(status().isOk()) // Check if the status is OK (200)
					.andReturn(); // Capture the result

			// Check if the HTTP status is OK (200)
			statusOk = result.getResponse().getStatus() == 200;

			// Check if the response contains "name"
			containsName = result.getResponse().getContentAsString().contains("name");

			// Check if the response contains "email"
			containsEmail = result.getResponse().getContentAsString().contains("email");

		} catch (Exception ex) {
			// If any exception occurs, we log it and ensure `yakshaAssert` is called with
			// "false"
			System.out.println("Error occurred: " + ex.getMessage());
		}

		// Combine all the results and pass them to yakshaAssert
		boolean finalResult = statusOk && containsName && containsEmail;

		// Use yakshaAssert to check if all assertions passed
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
