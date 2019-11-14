package testTierUtils;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

	private static Map<String, Object> scenarioContext = new HashMap<String, Object>();

	public static void setContext(Keys key, Object value) {
		scenarioContext.put(key.toString(), value);
	}

	public static Object getContext(Keys key) {
		return scenarioContext.get(key.toString());
	}

	public static Boolean isContains(Keys key) {
		return scenarioContext.containsKey(key.toString());
	}

	public static Object getContext(String arg) {
		System.out.println(scenarioContext.get(arg));
		
		return scenarioContext.get(arg.toString());
	}

}
