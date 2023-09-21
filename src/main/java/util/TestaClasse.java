package util;

import servico.Autorizacao;

public class TestaClasse {
String token = "{\"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJERTBPVEF3UVVVMk16Z3hPRUpGTkVSRk5qUkRNakkzUVVFek1qZEZOVEJCUkRVMlJrTTRSZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgudGhvbXNvbnJldXRlcnMuY29tLyIsInN1YiI6InpEbE1OeDBRR09Vam9XQ0NsV0VDNmRQTVZjeUZkOU1CQGNsaWVudHMiLCJhdWQiOiI0MDlmOTFmNi1kYzE3LTQ0YzgtYTVkOC1lMGExYmFmZDhiNjciLCJpYXQiOjE2OTUyMTkyMjQsImV4cCI6MTY5NTMwNTYyNCwiYXpwIjoiekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUIiLCJzY29wZSI6Imh0dHBzOi8vYXBpLnRob21zb25yZXV0ZXJzLmNvbS9hdXRoL29udmlvLWJyLmludm9pY2UtaW50ZWdyYXRpb24ud3JpdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.QfvmwMagl8IFCu9zw3BGZP4yNUheLZO8_-Ws-r_CtfFRDTQDQf8qT0Qe5qI5wk1wK4vNVg_r63djvvnaR9UmOEkZ4Rl5haA4jStjs3_f5xpMDkAW4yQL7Fqf8ty-uOrM5I49PuTmtFR-QWyZZz4hQxl1cZEuRFms3fmd8n-jpBIpYpUtTCwRBPyn77FULQARp-pAN8u2QS05z6jnIYWAC0DxlvUjiRjWXEELA76P4vNsBnasuN0XplkR-pzV_BqDoFVZeGsWzr8zTks1Y4GY7yx9jc7cFJbTl-0fSmYtmTC6OZX8Sp7kWAywYmjzXVjjy7UV3zoYNY6UTsVBhc2Qtg\",\"scope\":\"https://api.thomsonreuters.com/auth/onvio-br.invoice-integration.write\",\"expires_in\":86400,\"token_type\":\"Bearer\"}\r\n";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Autorizacao aut = new Autorizacao();
		System.out.println(aut.retornaToken());
	}

}
