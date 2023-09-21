package servico;

import java.io.IOException;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.Util;



public class Autorizacao {
	public String retornaToken() {
		 String token="";
		 Util u = new Util();
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=zDlMNx0QGOUjoWCClWEC6dPMVcyFd9MB&client_secret=A_PD7SPh1hR_g87EICtXPquT1mFecg07wqTFcwx9V2-1QyqT-MNzAHiGVFDsuEHF&audience=409f91f6-dc17-44c8-a5d8-e0a1bafd8b67");
				Request request = new Request.Builder()
				  .url("https://auth.thomsonreuters.com/oauth/token")
				  .method("POST", body)
				  .addHeader("Cookie", "did=s%3Av0%3A145b8a90-ea57-11eb-ae8a-877f15a4a518.QhUcTCGsMP28yWAB%2BYsUUZ5Gw4Srxf%2F0IDRkKPUQQHs; did_compat=s%3Av0%3A145b8a90-ea57-11eb-ae8a-877f15a4a518.QhUcTCGsMP28yWAB%2BYsUUZ5Gw4Srxf%2F0IDRkKPUQQHs; did=s%3Av0%3A08002410-57b8-11ee-aca3-33a0e7a50949.jVNmplRrZ8KyVM%2BYX6DNvLnrR9H6%2FPEbLaNQfY4vCIE; did_compat=s%3Av0%3A08002410-57b8-11ee-aca3-33a0e7a50949.jVNmplRrZ8KyVM%2BYX6DNvLnrR9H6%2FPEbLaNQfY4vCIE")
				  .addHeader("Content-Type", "application/x-www-form-urlencoded")
				  .addHeader("Authorization", "Basic ekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUI6QV9QRDdTUGgxaFJfZzg3RUlDdFhQcXVUMW1GZWNnMDd3cVRGY3d4OVYyLTFReXFULU1OekFIaUdWRkRzdUVIRg==")
				  .addHeader("x-integration-key", "GYBtKWR5QjSwVqtRNHM2ij4uPiM9AUGcg0Pm_rW6_Y0")
				  .build();
				try {
					Response response = client.newCall(request).execute();
					if(response.isSuccessful()) {
						 token = u.retornaResultadoConsulta(new JSONObject(response.body().source().readUtf8().toString()));
					}else {
						token = "";
					}
					System.out.println("Token: "+token);
					response.body().close();
					client.connectionPool().evictAll(); //limpa a piscina de conexao
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return token;
	}
}
