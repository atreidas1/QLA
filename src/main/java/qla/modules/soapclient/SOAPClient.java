package qla.modules.soapclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class SOAPClient {
	private static String soapEnvelope = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header></SOAP-ENV:Header><SOAP-ENV:Body>{{body}}</SOAP-ENV:Body></SOAP-ENV:Envelope>";

	
	/**
	 * @param toHost - URL of server.
	 * @param request - request source.
	 * @param action - soap Action if need, request will be wrapped in it.
	 * @param isNeedAddEnvelope - is need or not add soap evelope.
	 * @return response.
	 * @throws IOException
	 */
	public static String sendRequest(String toHost, String request, String action, SystemRequestParameters parameters) throws IOException{
		URL url = new URL(toHost);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setReadTimeout(parameters.getReadTimeout());
		connection.setConnectTimeout(parameters.getConnectTimeout());
		for (Map.Entry<String, String> param: parameters.getHeaders().entrySet()) {
			connection.setRequestProperty(param.getKey(), param.getValue());
		}
		connection.setRequestMethod("POST");
		String body = (action != null && !action.isEmpty() && parameters.isWrapInAction()) ? 
				"<"+action+">" + request +"</" +action +">"
				: request;
		String rq = parameters.isWrapInEnvelope() ? soapEnvelope.replace("{{body}}", body) : request;
		OutputStream os = connection.getOutputStream();
		os.write(rq.getBytes("UTF-8"));    
		os.close();
		int HttpResult = connection.getResponseCode();
		BufferedReader bufferedReader;
		StringBuilder resp = new StringBuilder();
		if(HttpResult == HttpURLConnection.HTTP_OK){
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = bufferedReader.readLine())!=null) {
				resp.append(line);
			}
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			String line = null;
			while ((line = bufferedReader.readLine())!=null) {
				resp.append(line);
			}
		}
		bufferedReader.close();
		connection.disconnect();
		return resp.toString();
	}
}
