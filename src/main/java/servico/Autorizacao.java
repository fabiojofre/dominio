package servico;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import conexao.ConexaoServidor;
import dominio.Retorno;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import util.Util;



public class Autorizacao {
	
	private String idEnvio;
	
	
	public String retornaToken(String x_integration_key, String client_id, String client_secret, String audience) {
	String token ="";
		 Util u = new Util();
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id="+client_id+"&client_secret="+client_secret+"&audience="+audience);
				Request request = new Request.Builder()
				  .url("https://auth.thomsonreuters.com/oauth/token")
				  .method("POST", body)
				  .addHeader("Cookie", "did=s%3Av0%3A145b8a90-ea57-11eb-ae8a-877f15a4a518.QhUcTCGsMP28yWAB%2BYsUUZ5Gw4Srxf%2F0IDRkKPUQQHs; did_compat=s%3Av0%3A145b8a90-ea57-11eb-ae8a-877f15a4a518.QhUcTCGsMP28yWAB%2BYsUUZ5Gw4Srxf%2F0IDRkKPUQQHs; did=s%3Av0%3A08002410-57b8-11ee-aca3-33a0e7a50949.jVNmplRrZ8KyVM%2BYX6DNvLnrR9H6%2FPEbLaNQfY4vCIE; did_compat=s%3Av0%3A08002410-57b8-11ee-aca3-33a0e7a50949.jVNmplRrZ8KyVM%2BYX6DNvLnrR9H6%2FPEbLaNQfY4vCIE")
				  .addHeader("Content-Type", "application/x-www-form-urlencoded")
				  .addHeader("Authorization", "Basic ekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUI6QV9QRDdTUGgxaFJfZzg3RUlDdFhQcXVUMW1GZWNnMDd3cVRGY3d4OVYyLTFReXFULU1OekFIaUdWRkRzdUVIRg==")
				  .addHeader("x-integration-key", x_integration_key)
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
				ConexaoServidor con = new ConexaoServidor();
				String UPDATE_TOKEN = "update dominio_api.token set token = ? where id_loja = ?";
				try {
					con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
					PreparedStatement stmt_up = con.prepareStatement(UPDATE_TOKEN);
					stmt_up.setString(1,token);
					stmt_up.setInt(2, Config.loja);
					int rowsInserted = stmt_up.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Update token executado!");
					} else {
						System.out.println("Update token falhou!");
					}
					con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return token;
	}
	
	
	public String retornaChaveIntegracao(String token, String x_integration_key) {
		String chave = "";
		Util u = new Util();
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("https://api.onvio.com.br/dominio/integration/v1/activation/enable")
				  .method("POST", body)			//Bearer + token gerado	
				  .addHeader("Authorization", "Bearer "+token)
				  .addHeader("x-integration-key", x_integration_key)//Chave fornecida pelo contador
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
				ConexaoServidor con = new ConexaoServidor();
				String UPDATE_CHAVE = "update dominio_api.token set chave = ? where id_loja = ?";
				try {
					con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
					PreparedStatement stmt_up = con.prepareStatement(UPDATE_CHAVE);
					stmt_up.setString(1,chave);
					stmt_up.setInt(2, Config.loja);
					int rowsInserted = stmt_up.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Update chave executado!");
					} else {
						System.out.println("Update chave falhou!");
					}
					con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return chave;
	}
	
	
	public String confirmaDadosCliente(String token, String x_integration_key) {
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = null;
		try {
			 response = Unirest.get("https://api.onvio.com.br/dominio/integration/v1/activation/info")
			  .header("Authorization", "Bearer "+token)
			  .header("x-integration-key", x_integration_key)
			  .asString();
		
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getBody();
		
	}
	
	public Retorno enviaXml(String token, String x_integration_key, String arquivo) {
		Retorno ret = new Retorno();
		Util u = new Util();
		String retorno = "";
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(60, TimeUnit.SECONDS);
		builder.readTimeout(60, TimeUnit.SECONDS);
		builder.writeTimeout(60, TimeUnit.SECONDS);
		OkHttpClient client = builder.build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				  .addFormDataPart("file[]",arquivo,
				    RequestBody.create(MediaType.parse("application/octet-stream"),
				    new File(arquivo)))
				  .addFormDataPart("query", null,
				     RequestBody.create(MediaType.parse("application/json"), "{\"boxe/File\": true}".getBytes()))
				  .build();
				Request request = new Request.Builder()
				  .url("https://api.onvio.com.br/dominio/invoice/v3/batches")
				  .method("POST", body)
				  .addHeader("x-integration-key", x_integration_key)
				  .addHeader("Authorization", "Bearer "+token)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					retorno = response.body().source().readUtf8().toString();
					
					//limpa a piscina de conexao
					Call call = client.newCall(request);
					response.body().close();
					client.connectionPool().evictAll(); 
					ret = u.retornaRetorno(retorno);
					
				} catch (IOException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Erro na conex�o com o servidor! \n"+e);
					retorno = "Erro na conex�o com o servidor! \nVerifique sua internet ou entre em contato com a Dominio!\n"+e.getMessage();
					return null;
				}
				return ret;
	}
	
	
	
	public Retorno confirmaProcessamento(String token, String x_integration_key, String processamento ) {
		String retorno = ""; 
		Retorno ret = new Retorno();
		Util u = new Util();
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(60, TimeUnit.SECONDS);
		builder.readTimeout(60, TimeUnit.SECONDS);
		builder.writeTimeout(60, TimeUnit.SECONDS);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://api.onvio.com.br/dominio/invoice/v3/batches/"+processamento)
				.addHeader("x-integration-key", x_integration_key)
				.addHeader("Authorization", "Bearer "+token)
				.build();

		  	Response response;
			try {
				response = client.newCall(request).execute();
				retorno = response.body().string();
				
				//limpa a piscina de conexao
				Call call = client.newCall(request);
				response.body().close();
				client.connectionPool().evictAll(); 
				ret = u.retornaRetorno(retorno);;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ret;
		
	}
	private int trataMensagemRetorno(String mensagem) {
		int ret =0;
		if(mensagem.contains("Aguardando processamento")) {
			ret=1;
			int inicio = mensagem.indexOf("id") + 5;
			int fim = mensagem.indexOf("status", inicio);
			String msg = mensagem.substring(inicio, fim-3);
			setIdEnvio(msg); 
			System.out.println("Aguardando processamento");
		}else if(mensagem.contains("Failed to decode invalid or incorrectly formatted token") || (mensagem.contains("Token has expired"))){
			ret = 2;
			System.out.println("Failed to decode invalid or incorrectly formatted token");
		}else if(mensagem.contains("Client Not Enabled")) {
			ret = 3;
			JOptionPane.showMessageDialog(null, "Cliente n�o cadastrado corretamente ou n�o habilitado! Favor revisar os par�metros!");
		}else 
		System.out.println(mensagem);
		return ret;
	}

	

	public String getIdEnvio() {
		return idEnvio;
	}


	private void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}

	
}
