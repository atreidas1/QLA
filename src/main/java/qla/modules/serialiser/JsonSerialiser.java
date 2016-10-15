package qla.modules.serialiser;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonSerialiser {
	private static Gson gson;
	static
	{
		gson = new Gson();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object deserialise(String json, Class class1) {
		return gson.fromJson(json, class1);
	}
	
	public static String serialise(Object object) {
		return gson.toJson(object);
	}

	public static Object deserialise(String json, Type type) {
		return gson.fromJson(json, type);
		
	}
}
