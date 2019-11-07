package apihelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	private static String configFilePath = "Config/configuration.properties";

	public static String readConfig(String key) {
		String value = "";
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(configFilePath);
			prop.load(input);
			value = prop.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static String getReportConfigPath() {
		
		 String ConfigurereportConfigPath =
		 PropertyReader.readConfig(ConfigurationProperties.ReportConfigPath);
		 String reportConfigPath = System.getProperty("user.dir") +
		 ConfigurereportConfigPath;
		 return reportConfigPath;
		

	}

}
