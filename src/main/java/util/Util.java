package util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dominio.ApiStatus;
import dominio.BoxeStatus;
import dominio.FilesExpanded;
import dominio.Retorno;
import dominio.Status;

public class Util {

	public String trataToken(JSONObject json) {
		
//		 Extrai apenas o array do bloco mensagem do JSon 		
		String dados = json.getString("access_token");
		return dados;
	}
	public String trataChaveIntegracao(JSONObject json) {
		
//		 Extrai apenas o array do bloco mensagem do JSon 		
		String dados = json.getString("integrationKey");
		return dados;
	}
	public String[] trataValoresChaveIntegracao(JSONObject json) {
		
//		 Extrai apenas o array do bloco mensagem do JSon 		
		String[] dados  = null;
		dados[0]=json.getString("accountantOfficeName");
		dados[1]=json.getString("accountantOfficeNationalIdentity");
		dados[2]=json.getString("clientName");
		dados[3]=json.getString("clientNationalIdentity");
		
		return dados;
	}

	
	
	public Retorno retornaRetorno(String json) {
		Retorno ret = new Retorno();
		if(json.contains("Failed to decode invalid or incorrectly formatted token") || (json.contains("Token has expired"))) {
			ret.setStatus(new Status("TO", "Falha no token"));
		}else {
		try {
		     JSONObject jsonRetorno = new JSONObject(json);
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
		      ret = null;
		    }
		}
		return ret;
	
	}
	
}
