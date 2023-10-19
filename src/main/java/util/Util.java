package util;


import org.json.JSONObject;

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

}
