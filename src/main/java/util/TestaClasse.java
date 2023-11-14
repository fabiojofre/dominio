package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import servico.Arquivo;
import servico.Autorizacao;
import servico.Config;
import servico.EnviaXML;
import servico.ServicoConfig;

public class TestaClasse {

	static String arquivo = "C:\\dominio\\NFe50220352005378000696550020006601261076629947.xml";
	
	static String mensagemOK = "{\r\n"
			+ "    \"type\": \"batchV2Dto\",\r\n"
			+ "    \"lastStatusOn\": \"2023-11-13T12:12:22.743\",\r\n"
			+ "    \"apiVersion\": \"v2\",\r\n"
			+ "    \"boxeFile\": false,\r\n"
			+ "    \"filesExpanded\": [\r\n"
			+ "        {\r\n"
			+ "            \"lastApiStatusOn\": \"2023-11-13T12:12:22.882\",\r\n"
			+ "            \"lastBoxeStatusOn\": \"2023-11-13T12:12:22.733\",\r\n"
			+ "            \"apiStatus\": {\r\n"
			+ "                \"code\": \"EA13\",\r\n"
			+ "                \"message\": \"Empresa não encontrada. Envie um arquivo com a inscrição da empresa.\"\r\n"
			+ "            },\r\n"
			+ "            \"boxeStatus\": {\r\n"
			+ "                \"code\": \"SB2\",\r\n"
			+ "                \"message\": \"O arquivo não será enviado ao BOX-e.\"\r\n"
			+ "            }\r\n"
			+ "        }\r\n"
			+ "    ],\r\n"
			+ "    \"id\": \"826936117F2F49358AC80D04EA7223F9\",\r\n"
			+ "    \"status\": {\r\n"
			+ "        \"code\": \"S2\",\r\n"
			+ "        \"message\": \"Processado.\"\r\n"
			+ "    }\r\n"
			+ "}";
	static String mensagemErro1 ="400\r\n"
			+ "\r\n"
			+ "            {\r\n"
			+ "                \"type\": \"Bad Request\",\r\n"
			+ "                \"title\": \"Bad Request\",\r\n"
			+ "                \"detail\": \"Failed to decode invalid or incorrectly formatted token.\",\r\n"
			+ "                \"status\": \"400\",\r\n"
			+ "                \"instance\": \"/dominio/invoice/v3/batches\",\r\n"
			+ "                \"api_requestId\": \"rrt-0d94ee2a277cb8029-b-sa-15017-3479160-2\"\r\n"
			+ "            }";
	
	
public static void main(String[] args) {
		// TODO Auto-generated method stub

		Autorizacao aut = new Autorizacao();
//		System.out.println(aut.confirmaDadosCliente(token, x_integration_key));
//		System.out.println(aut.retornaToken(x_integration_key));
//		System.out.println(aut.retornaChaveIntegracao(token, x_integration_key));
//		System.out.println(aut.enviaXML(token, x_integration_key));
		
	
		ServicoConfig s = new ServicoConfig();
		s.trataConfig();
		
//		EnviaXML gera = new EnviaXML();
//		gera.geraNotaSaida();
//		System.out.println(aut.enviaXml(Config.token,Config.x_integration_key,arquivo));
//		System.out.println(aut.retornaToken(Config.x_integration_key, Config.client_id, Config.client_secret, Config.audience));
		
		Arquivo arquivo = new Arquivo();
		arquivo.escreverLog("Teste de Log");
//		List<String> arquivos = arquivo.listarArquivosXML(dirNFCe);
////		arquivo.deletarArquivo();
//		int val = 0;
//		for(int i =0; i < arquivos.size(); i++) {
//			System.out.println(arquivos.get(i) );
//			val=val+1;
//		}
//		arquivo.deletarArquivo("C:\\temp\\fenix\\nfce\\NFCe26230904887419000168650540001894951054006291.xml");
//		
//		System.out.println(aut.retornaToken(Config.x_integration_key, Config.client_id, Config.client_secret, Config.audience));
		
//		System.out.println(trataMensagemRetorno(mensagemErro1));
		
		
//		 
	}

//	public static int trataMensagemRetorno(String mensagem) {
//		int ret =0;
//		if(mensagem.contains("Aguardando processamento")) {
//			ret=1;
//		} if(mensagem.contains("Failed to decode invalid or incorrectly formatted token")){
//			ret = 2;
//		} if(mensagem.contains("Client Not Enabled")) {
//			ret = 3;
//		}else JOptionPane.showMessageDialog(null, mensagem);
//		return ret;
//	}
	//https://www.mballem.com/post/manipulando-arquivo-txt-com-java/

}
