package util;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dominio.ApiStatus;
import dominio.BoxeStatus;
import dominio.FilesExpanded;
import dominio.Retorno;
import dominio.Status;

public class TestaClasse {
	
	static String str = "{"
			+ "	\"type\":\"batchV2Dto\",\r\n"
			+ "	\"lastStatusOn\":\"2023-12-05T13:00:23.537\",\r\n"
			+ "	\"apiVersion\":\"v2\",\r\n"
			+ "	\"boxeFile\":false,\r\n"
			+ "	\"filesExpanded\":[\r\n"
			+ "					{\r\n"
			+ "						\"lastApiStatusOn\":\"2023-12-05T13:00:23.685\",\r\n"
			+ "						\"lastBoxeStatusOn\":\"2023-12-05T13:00:23.526\",\r\n"
			+ "						\"apiStatus\":{\r\n"
			+ "										\"code\":\"EA13\",\r\n"
			+ "										\"message\":\"Empresa não encontrada. Envie um arquivo com a inscrição da empresa.\"\r\n"
			+ "									},\r\n"
			+ "						\"boxeStatus\":{\r\n"
			+ "										\"code\":\"SB2\",\r\n"
			+ "										\"message\":\"O arquivo não será enviado ao BOX-e.\"\r\n"
			+ "									}\r\n"
			+ "					}\r\n"
			+ "					],\r\n"
			+ "	\"id\":\"127509D09597441AB570BAEA17E03701\",\r\n"
			+ "	\"status\":{\r\n"
			+ "				\"code\":\"S2\",\r\n"
			+ "				\"message\":\"Processado.\"\r\n"
			+ "			}\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Util u = new Util();
		System.out.println(u.retornaRetorno(str).toString());
		
	
	}
	
	public static Retorno retornaRetorno(String json) {
		Retorno ret = new Retorno();
		
		try {
		     JSONObject jsonRetorno = new JSONObject(str);
		      JSONArray jsonFilesExpanded = jsonRetorno.getJSONArray("filesExpanded");
		      JSONObject jsonStatus = jsonRetorno.getJSONObject("status");

		      ret.setType(jsonRetorno.getString("type"));
		      ret.setLastStatusOn(jsonRetorno.getString("lastStatusOn"));
		      ret.setApiVersion(jsonRetorno.getString("apiVersion"));
		      ret.setBoxeFile(jsonRetorno.getBoolean("boxeFile"));
		      ret.setId(jsonRetorno.getString("id"));
		      ret.setStatus(new Status(jsonStatus.getString("code"), jsonStatus.getString("message")));
		      	      
		      for(int i =0;i<jsonFilesExpanded.length();i++) {
		    	  // selarei os JSONs
		    	  JSONObject object = jsonFilesExpanded.getJSONObject(i);
		    	  JSONObject apiStatus = object.getJSONObject("apiStatus");
		    	  JSONObject boxeStatus = object.getJSONObject("boxeStatus");
		    	  // distrinchei os subobjetos
		    	  ApiStatus a = new ApiStatus(apiStatus.getString("code"),apiStatus.getString("message"));
		    	  BoxeStatus b = new BoxeStatus(boxeStatus.getString("code"),boxeStatus.getString("message"));
		    	  // conclui o objeto superior
		    	  ret.setFilesExpanded(new FilesExpanded(object.getString("lastApiStatusOn"), object.getString("lastBoxeStatusOn"), a, b));
		      }	 
		      
		    } catch (JSONException err) {
		      System.out.println("Exception : " + err.toString());
		    }
		
		return ret;
	}
}
