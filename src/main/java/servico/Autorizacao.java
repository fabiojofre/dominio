package servico;

import java.io.IOException;


import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
						 token = u.trataToken(new JSONObject(response.body().source().readUtf8().toString()));
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
	
	
	public String retornaChaveIntegracao() {
		String chave = "";
		Util u = new Util();
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("https://api.onvio.com.br/dominio/integration/v1/activation/enable")
				  .method("POST", body)			//Bearer + token gerado	
				  .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJERTBPVEF3UVVVMk16Z3hPRUpGTkVSRk5qUkRNakkzUVVFek1qZEZOVEJCUkRVMlJrTTRSZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgudGhvbXNvbnJldXRlcnMuY29tLyIsInN1YiI6InpEbE1OeDBRR09Vam9XQ0NsV0VDNmRQTVZjeUZkOU1CQGNsaWVudHMiLCJhdWQiOiI0MDlmOTFmNi1kYzE3LTQ0YzgtYTVkOC1lMGExYmFmZDhiNjciLCJpYXQiOjE2OTUzMDM4MTMsImV4cCI6MTY5NTM5MDIxMywiYXpwIjoiekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUIiLCJzY29wZSI6Imh0dHBzOi8vYXBpLnRob21zb25yZXV0ZXJzLmNvbS9hdXRoL29udmlvLWJyLmludm9pY2UtaW50ZWdyYXRpb24ud3JpdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.p35ksnRr-xxugGaVv9y9xhoEUT3HkE69wmLe5FjLIQJiAgrxYV_shk0irzbkodtORNnagv2IfqoNNumVSX02NDsu-4rWuMLtdYw26wUcL8SE6YUErfvydVjk3kSZtMdTyHZfpor35pLMIl2qMoqMqxgczriv_x5Bypz_EjamJFDAZj4KgXewPhCBs_2X346XJGpUblqEUe2hMyCsgticmS6eDom74J2Lh0z25wNUhHiHcAU3KIqVRjcLH5q4VfXVcNuC8KiymeFqpda5wN84K2A2vgJsipxZbLzYR8qiloU2qNk76718vqIXpuJ5ovjZcO-RM8wZ3V_GEl8T_qEOVw")
				  .addHeader("x-integration-key", "GYBtKWR5QjSwVqtRNHM2ij4uPiM9AUGcg0Pm_rW6_Y0")//Chave fornecida pelo contador
				  .build();
				try {
					Response response = client.newCall(request).execute();
					if(response.isSuccessful()) {
						 chave = u.trataChaveIntegracao(new JSONObject(response.body().source().readUtf8().toString()));
						 System.out.println(response.isSuccessful());
					}else {
						chave = "";
						System.out.println(response.isSuccessful());
						System.out.println(new JSONObject(response.body().source().readUtf8().toString()));
					}
					response.body().close();
					client.connectionPool().evictAll(); //limpa a piscina de conexao
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return chave;
	}
	
	
	public String confirmaDadosCliente() {
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = null;
		try {
			 response = Unirest.get("https://api.onvio.com.br/dominio/integration/v1/activation/info")
			  .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJERTBPVEF3UVVVMk16Z3hPRUpGTkVSRk5qUkRNakkzUVVFek1qZEZOVEJCUkRVMlJrTTRSZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgudGhvbXNvbnJldXRlcnMuY29tLyIsInN1YiI6InpEbE1OeDBRR09Vam9XQ0NsV0VDNmRQTVZjeUZkOU1CQGNsaWVudHMiLCJhdWQiOiI0MDlmOTFmNi1kYzE3LTQ0YzgtYTVkOC1lMGExYmFmZDhiNjciLCJpYXQiOjE2OTc3MTY4NzcsImV4cCI6MTY5NzgwMzI3NywiYXpwIjoiekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUIiLCJzY29wZSI6Imh0dHBzOi8vYXBpLnRob21zb25yZXV0ZXJzLmNvbS9hdXRoL29udmlvLWJyLmludm9pY2UtaW50ZWdyYXRpb24ud3JpdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.pjQAvaVp3fmHJg7mz8DkGQSTZsrSphvhBNo66hFCaq5GBnxrVR7e0S0WCb5Jr8H64BU25h_ic2AbTlXFbUnI3kS8WRVF1zJV3IEf0m7KLXMlajz_8LwdisQcUMeleKMx4phkqhrZ0s4It_YBLRoV9kQiTcDobTXTsucnASJ06Vfv5egNL0_HDH0U3aJnaOGp5NrrBbGeE0UpjIW2juVy4aX2Dw_MKU79P5rHz4x4dWyGvAyCzCVvRbt7kRZ3ntCnYRxCN_nA4uIdO3lQzWHGbE-SZLRdCbuhOfpKjO4Lvee5QNuJRKwf3nTU7mKnMSl-mKPi0Rm5SAgSi2mpscb-gQ")
			  .header("x-integration-key", "GYBtKWR5QjSwVqtRNHM2ij4uPiM9AUGcg0Pm_rW6_Y0")
			  .asString();
		
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getBody();
		
	}
}
