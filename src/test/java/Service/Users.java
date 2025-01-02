package Service;

import BaseUrl.Endpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Users extends Endpoints {

	public static final String TodoListEndpoint = "/todos";
	public static final String UserAddressMapper = "/users";

	public Users() {
		RestAssured.baseURI = FancodeUrl;
	}

	public Response ToDoListOfUsers() {

		Response response = RestAssured.given().relaxedHTTPSValidation().log().all().when().get(TodoListEndpoint);

		response.then().log().all();

		return response;
	}

	public Response FanCodeCityUsers() {
		Response response = RestAssured.given().relaxedHTTPSValidation().log().all().when().get(UserAddressMapper);

		response.then().log().all();
		return response;
	}

}
