package dm.testtier.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<String, String> readJsonFile(String jsonFilePath, String Environment, String Client) throws IOException {
		HashMap<String, String> config_map = new HashMap<String, String>();
		Object obj;
		try {
			obj = new JSONParser().parse(new FileReader(jsonFilePath));
			JSONObject jo = (JSONObject) obj;

			Map client = ((Map) jo.get(Client));
			Map env = (Map) client.get(Environment);
			Iterator<Map.Entry> itr1 = env.entrySet().iterator();
			while (itr1.hasNext()) {

				Map.Entry pair = itr1.next();
				config_map.put(pair.getKey().toString(), pair.getValue().toString());
				
			}
		} catch (Exception ex) {
			System.out.println("Exception while reading Json file : " + ex.getMessage());
		}
		return config_map;
	}
	
}
