package util;

import java.util.List;

import servico.Arquivo;
import servico.ServicoConfig;

public class TestaClasse {
static String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJERTBPVEF3UVVVMk16Z3hPRUpGTkVSRk5qUkRNakkzUVVFek1qZEZOVEJCUkRVMlJrTTRSZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgudGhvbXNvbnJldXRlcnMuY29tLyIsInN1YiI6InpEbE1OeDBRR09Vam9XQ0NsV0VDNmRQTVZjeUZkOU1CQGNsaWVudHMiLCJhdWQiOiI0MDlmOTFmNi1kYzE3LTQ0YzgtYTVkOC1lMGExYmFmZDhiNjciLCJpYXQiOjE2OTkwMTY4OTcsImV4cCI6MTY5OTEwMzI5NywiYXpwIjoiekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUIiLCJzY29wZSI6Imh0dHBzOi8vYXBpLnRob21zb25yZXV0ZXJzLmNvbS9hdXRoL29udmlvLWJyLmludm9pY2UtaW50ZWdyYXRpb24ud3JpdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.P4OpYCqbexcAGSOFh3lKfE79Vht6VH9fpdSXiII5wb_Ftyqme6ZvPHah3i20bcUcXLpccGfn84JJVQhFokyIEGEmRpVGXfrOQikdfWMIv7gnPoPKJPmBOYOpBxhjAfh7RI3Fx8zXmsLhNm1vrMJVa7no4VDsdJfeZ7zJKkqXVKmI0mQ8ThC4soMsX8cDwH6o-PmpHh-toYw4XZ6X4-OIxoN3fGuVn9IDetnMtQbbQWf_4oruRtJ-Z-V9h6ORR7OwYMi9J0UiORIXjgqpvlUn9WincK7_mmmrVT-Xcn6HrQnN-LFWlzVfDiL6yJejdxJEFkPJR22twVm5lByiC4DHOA";
static String x_integration_key ="GYBtKWR5QjSwVqtRNHM2igEmyGkr5Elih5QI6jLGeXk";
static String arquivo = "C:\\temp\\fenix\\nfce\\NFCe26230904887419000168650540001895081054006429.xml";
static String dirNFCe = "C:\\temp\\fenix\\nfce";
static String dirNFentrada = "C:\temp\fenix\nfce";
static String dirNFsaida = "C:\temp\fenix\nfce";

public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Autorizacao aut = new Autorizacao();
//		System.out.println(aut.confirmaDadosCliente(token, x_integration_key));
//		System.out.println(aut.retornaToken(x_integration_key));
//		System.out.println(aut.retornaChaveIntegracao(token, x_integration_key));
//		System.out.println(aut.enviaXML(token, x_integration_key));
//		System.out.println(aut.enviaXml(token,x_integration_key,arquivo));
		
		
		Arquivo arquivo = new Arquivo();
		List<String> arquivos = arquivo.listarArquivosXML(dirNFCe);
//		arquivo.deletarArquivo();
		int val = 0;
		for(int i =0; i < arquivos.size(); i++) {
			System.out.println(arquivos.get(i) );
			val=val+1;
		}
		System.out.println(val);
		
		
		ServicoConfig s = new ServicoConfig();
		s.trataConfig();
	}

	
	

}
