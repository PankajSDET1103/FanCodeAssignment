package BaseUrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Endpoints {

	public static String FancodeUrl;

	public Endpoints() {

		readPropertyFile();
	}

	private void readPropertyFile() {

		String userDir = System.getProperty("user.dir");

		String propertyFile = null;

		propertyFile = System.getProperty("propertyFile", "config.properties");

		try (InputStream inputStream = new FileInputStream(userDir + "/src/main/resources/" + propertyFile)) {

			Properties properties = new Properties();
			properties.load(inputStream);

			FancodeUrl = properties.getProperty("fanCodeUrl");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
