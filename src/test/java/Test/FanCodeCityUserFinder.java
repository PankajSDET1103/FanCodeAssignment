package Test;

import java.util.List;

import org.junit.Assert;
import org.testng.asserts.SoftAssert;

import Service.Users;
import Utilities.HelperClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class FanCodeCityUserFinder extends HelperClass {

	private final HelperClass helperClass = new HelperClass();
	private final Users users = new Users();

	Response TodoResponse;
	Response UsersResponse;

	private List<Integer> FanCodeCityUserIds;

	@Given("User has the todo tasks")
	public void GetTodoTaskList() {
		TodoResponse = users.ToDoListOfUsers();
		Assert.assertEquals(TodoResponse.statusCode(), 200);

	}

	@Given("User belongs to the city FanCode")
	public void FanCodeCityUser() {
		UsersResponse = users.FanCodeCityUsers();
		Assert.assertEquals(UsersResponse.statusCode(), 200);
		FanCodeCityUserIds = helperClass.getUsersInFanCodeCity(UsersResponse);
		System.out.println("FanCode City Users " + FanCodeCityUserIds);
	}

	@Then("User Completed task percentage should be greater than 50%")
	public void FanCodeUserTaskCompletionPercentage() {
		SoftAssert softAssert = new SoftAssert();

		for (int userId : FanCodeCityUserIds) {
			double completionPercentage = helperClass.calculateCompletedTaskPercentage(TodoResponse, userId);

			// Print user task completion percentage
			System.out.println("User ID: " + userId + " - Task Completion: " + completionPercentage + "%");

			// Perform a soft assert to check if the completion percentage is greater than
			// 50%
			softAssert.assertTrue(completionPercentage > 50,
					"User ID " + userId + " has a task completion percentage below 50%.");
		}

		// Collate all assertions after all users are validated
		softAssert.assertAll();
	}
}
