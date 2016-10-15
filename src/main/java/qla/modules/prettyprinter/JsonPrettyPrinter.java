package qla.modules.prettyprinter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonPrettyPrinter {
	
	public static String prettyPrint(String string) {
		  String string2 = string.replaceAll("\n", "");
		  JsonParser parser = new JsonParser();
	      JsonObject json = parser.parse(string2).getAsJsonObject();
	      Gson gson = new GsonBuilder().setPrettyPrinting().create();
	      String prettyJson = gson.toJson(json);
	      return prettyJson;
	}
}
