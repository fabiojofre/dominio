package servico;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class Servico {

	
	private JSONObject resultadoConsulta;
	public static String token = "";

	    public JSONObject enviaXML(String apikey, String email, String xml){
	    	OkHttpClient client = new OkHttpClient().newBuilder().build();
	    	MediaType mediaType = MediaType.parse("application/json");
	    	 String webService = "https://api.sieg.com/aws/api-xml.ashx"+"?apikey="+apikey+"&email="+email;
			
				try {
				RequestBody body = RequestBody.create(mediaType, xml);
	    	
				Request request = new Request.Builder()
						  .url(webService.trim())
						  .method("POST", body)
						  .addHeader("Content-Type", "application/json")
						  .build();
				
				
					Call call = client.newCall(request);
								
					Response response = call.execute();
						
					if(response.isSuccessful()) {
						resultadoConsulta = new JSONObject(response.body().source().readUtf8().toString());
						
					}
					response.body().close();
					client.connectionPool().evictAll(); //limpa a piscina de conexao
					 return resultadoConsulta;
					 
				}catch (Exception e) {
					 
					System.out.println(e);
					return resultadoConsulta;
				}				
	    }
}
