package Utilities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.restassured.response.Response;

public class HelperClass {

	public boolean isUserInFanCodeCity(double lat, double lon) {
		return lat >= -40 && lat <= 5 && lon >= 5 && lon <= 100;
	}

	
	public List<Integer> getUsersInFanCodeCity(Response usersResponse) {
		return usersResponse.jsonPath().getList("$").stream().filter(user -> {
			Map<?, ?> address = (Map<?, ?>) ((Map<?, ?>) user).get("address");
			Map<?, ?> geo = (Map<?, ?>) address.get("geo");
			double lat = Double.parseDouble((String) geo.get("lat"));
			double lon = Double.parseDouble((String) geo.get("lng"));
			return isUserInFanCodeCity(lat, lon);
		}).map(user -> (Integer) ((Map<?, ?>) user).get("id")).collect(Collectors.toList());
	}

	public double calculateCompletedTaskPercentage(Response todosResponse, int userId) {
		List<Map<String, Object>> userTasks = todosResponse.jsonPath().getList("$");
		long totalTasks = 0;
		long completedTasks = 0;

		// Loop through all tasks and filter for the specified userId
		for (Map<String, Object> task : userTasks) {
			if (task.get("userId") instanceof Integer && task.get("completed") instanceof Boolean) {
				int taskUserId = (Integer) task.get("userId");
				boolean isCompleted = (Boolean) task.get("completed");

				if (taskUserId == userId) {
					totalTasks++;
					if (isCompleted) {
						completedTasks++;
					}
				}
			}
		}

		// If no tasks exist for the user, return 0; otherwise, calculate the completion
		// percentage
		return totalTasks == 0 ? 0 : ((double) completedTasks / totalTasks) * 100;
	}

}
