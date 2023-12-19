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
import servico.EnviaXML;
import servico.ServicoConfig;

public class TestaClasse {
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ServicoConfig c = new ServicoConfig();
		EnviaXML e = new EnviaXML();
		
		c.trataConfig();
		
		e.geraNotaEntrada();
	
	}
	
	
}
