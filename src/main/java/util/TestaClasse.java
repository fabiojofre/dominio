package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import servico.Autorizacao;
import servico.Config;
import servico.EnviaXML;
import servico.ServicoConfig;

public class TestaClasse {
static String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJERTBPVEF3UVVVMk16Z3hPRUpGTkVSRk5qUkRNakkzUVVFek1qZEZOVEJCUkRVMlJrTTRSZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgudGhvbXNvbnJldXRlcnMuY29tLyIsInN1YiI6InpEbE1OeDBRR09Vam9XQ0NsV0VDNmRQTVZjeUZkOU1CQGNsaWVudHMiLCJhdWQiOiI0MDlmOTFmNi1kYzE3LTQ0YzgtYTVkOC1lMGExYmFmZDhiNjciLCJpYXQiOjE2OTkwMTY4OTcsImV4cCI6MTY5OTEwMzI5NywiYXpwIjoiekRsTU54MFFHT1Vqb1dDQ2xXRUM2ZFBNVmN5RmQ5TUIiLCJzY29wZSI6Imh0dHBzOi8vYXBpLnRob21zb25yZXV0ZXJzLmNvbS9hdXRoL29udmlvLWJyLmludm9pY2UtaW50ZWdyYXRpb24ud3JpdGUiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.P4OpYCqbexcAGSOFh3lKfE79Vht6VH9fpdSXiII5wb_Ftyqme6ZvPHah3i20bcUcXLpccGfn84JJVQhFokyIEGEmRpVGXfrOQikdfWMIv7gnPoPKJPmBOYOpBxhjAfh7RI3Fx8zXmsLhNm1vrMJVa7no4VDsdJfeZ7zJKkqXVKmI0mQ8ThC4soMsX8cDwH6o-PmpHh-toYw4XZ6X4-OIxoN3fGuVn9IDetnMtQbbQWf_4oruRtJ-Z-V9h6ORR7OwYMi9J0UiORIXjgqpvlUn9WincK7_mmmrVT-Xcn6HrQnN-LFWlzVfDiL6yJejdxJEFkPJR22twVm5lByiC4DHOA";
static String x_integration_key ="GYBtKWR5QjSwVqtRNHM2igEmyGkr5Elih5QI6jLGeXk";
static String arquivo = "C:\\temp\\fenix\\nfce\\NFCeTeste.xml";
static String dirNFCe = "C:\\temp\\fenix\\nfce";
static String dirNFentrada = "C:\temp\fenix\nfce";
static String dirNFsaida = "C:\temp\fenix\nfce";

static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><nfeProc xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"4.00\"><NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"><infNFe Id=\"NFe43210608865810000112550020000000891000000037\" versao=\"4.00\"><ide><cUF>43</cUF><cNF>00000003</cNF><natOp>VENDA DE MERCAD. ADQ. OU REC. TERC. C/ S</natOp><mod>55</mod><serie>2</serie><nNF>89</nNF><dhEmi>2021-06-04T11:58:00-03:00</dhEmi><dhSaiEnt>2021-06-04T11:58:00-03:00</dhSaiEnt><tpNF>1</tpNF><idDest>1</idDest><cMunFG>4318705</cMunFG><tpImp>1</tpImp><tpEmis>1</tpEmis><cDV>7</cDV><tpAmb>1</tpAmb><finNFe>1</finNFe><indFinal>1</indFinal><indPres>1</indPres><procEmi>0</procEmi><verProc>3.19.1-149</verProc></ide><emit><CNPJ>08865810000112</CNPJ><xNome>VERA M DA SILVA REINHEIMER &amp; CIA LTDA</xNome><xFant>MERCADO REINHEIMER REDEFORT</xFant><enderEmit><xLgr>RUA EUGENIO BERGER</xLgr><nro>470</nro><xBairro>SCHARLAU</xBairro><cMun>4318705</cMun><xMun>SAO LEOPOLDO</xMun><UF>RS</UF><CEP>93125480</CEP><cPais>1058</cPais><xPais>BRASIL</xPais><fone>0000000000</fone></enderEmit><IE>1240244859</IE><CRT>3</CRT></emit><dest><CNPJ>40799593000112</CNPJ><xNome>SUPERMERCADO REINHEIMER E LAMPERT</xNome><enderDest><xLgr>PARANA</xLgr><nro>202</nro><xBairro>SCHARLAU</xBairro><cMun>4318705</cMun><xMun>SAO LEOPOLDO</xMun><UF>RS</UF><CEP>93120020</CEP><cPais>1058</cPais><xPais>BRASIL</xPais><fone>51999040887</fone></enderDest><indIEDest>1</indIEDest><IE>1240324054</IE></dest><entrega><CNPJ>40799593000112</CNPJ><xLgr>PARANA</xLgr><nro>202</nro><xBairro>SCHARLAU</xBairro><cMun>4318705</cMun><xMun>SAO LEOPOLDO</xMun><UF>RS</UF></entrega><det nItem=\"1\"><prod><cProd>28186</cProd><cEAN>7896052605316</cEAN><xProd>CERVEJA SCHIN 473ML</xProd><NCM>22030000</NCM><cBenef>RS052408</cBenef><CFOP>5405</CFOP><uCom>UN0001</uCom><qCom>1632.0000</qCom><vUnCom>2.480000</vUnCom><vProd>4047.36</vProd><cEANTrib>7896052605316</cEANTrib><uTrib>UN0001</uTrib><qTrib>1632.0000</qTrib><vUnTrib>2.480000</vUnTrib><indTot>1</indTot></prod><imposto><ICMS><ICMS60><orig>0</orig><CST>60</CST><vBCSTRet>0.00</vBCSTRet><pST>0.00</pST><vICMSSubstituto>587.52</vICMSSubstituto><vICMSSTRet>0.00</vICMSSTRet><pRedBCEfet>0.00</pRedBCEfet><vBCEfet>0.00</vBCEfet><pICMSEfet>0.00</pICMSEfet><vICMSEfet>0.00</vICMSEfet></ICMS60></ICMS><IPI><cEnq>999</cEnq><IPITrib><CST>99</CST><qUnid>1632.0000</qUnid><vUnid>0.0000</vUnid><vIPI>0.00</vIPI></IPITrib></IPI><PIS><PISNT><CST>04</CST></PISNT></PIS><COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS></imposto></det><total><ICMSTot><vBC>0.00</vBC><vICMS>0.00</vICMS><vICMSDeson>0.00</vICMSDeson><vFCP>0.00</vFCP><vBCST>0.00</vBCST><vST>0.00</vST><vFCPST>0.00</vFCPST><vFCPSTRet>0.00</vFCPSTRet><vProd>4047.36</vProd><vFrete>0.00</vFrete><vSeg>0.00</vSeg><vDesc>0.00</vDesc><vII>0.00</vII><vIPI>0.00</vIPI><vIPIDevol>0.00</vIPIDevol><vPIS>0.00</vPIS><vCOFINS>0.00</vCOFINS><vOutro>0.00</vOutro><vNF>4047.36</vNF><vTotTrib>0.00</vTotTrib></ICMSTot></total><transp><modFrete>0</modFrete><transporta><CNPJ>40799593000112</CNPJ><xNome>SUPERMERCADO REINHEIMER E LAMPERT</xNome><IE>1240324054</IE><xEnder>PARANA</xEnder><xMun>SAO LEOPOLDO</xMun><UF>RS</UF></transporta><vol><qVol>1632</qVol></vol></transp><cobr><fat><nFat>89</nFat><vOrig>4047.36</vOrig><vDesc>0.00</vDesc><vLiq>4047.36</vLiq></fat><dup><nDup>001</nDup><dVenc>2021-06-04</dVenc><vDup>4047.36</vDup></dup></cobr><pag><detPag><indPag>1</indPag><tPag>99</tPag><vPag>4047.36</vPag></detPag></pag><infAdic><infCpl>TRIBUTOS APROX: 662,96 (16,38%) FONTE: IBPT</infCpl></infAdic></infNFe><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><Reference URI=\"#NFe43210608865810000112550020000000891000000037\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><DigestValue>6tbQPla+ylJZ7OlfAmf6U9AjNKs=</DigestValue></Reference></SignedInfo><SignatureValue>ixknMHMzyA3E9RcSjJ6XWE1RsZJifG/QSiA1zlLzOpF0mNfqV9JjKV4dyamDM0V7ZniBNCUs4QIe\r\n"
		+ "uxrVxF9PlsD5BnCPhtcHJiDL8rprqz4xwsuqwrLCxm3THRC0Fezv9iS06b6rMj5eTW6/tPh49dKm\r\n"
		+ "oKO8z6b5vTDlIZIoauJJ89N5L3QMeN9erLAaLfoLKbNytA5rrrUTn93Cw5U3Nru2Loajoz+ztpZm\r\n"
		+ "cI0hu1CbmFjSPyAbVbHiobhTop/keOUGianM8ihWU4hcDUsVbjRJMYR573biraQGU7QRpscWPLFh\r\n"
		+ "hGEvb67MfgguXupRt8KkFIQ0VTewai3W1uJqOA==</SignatureValue><KeyInfo><X509Data><X509Certificate>MIIHxjCCBa6gAwIBAgIIe/ABXIXiRXUwDQYJKoZIhvcNAQELBQAwdjELMAkGA1UEBhMCQlIxEzAR\r\n"
		+ "BgNVBAoTCklDUC1CcmFzaWwxNjA0BgNVBAsTLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFs\r\n"
		+ "IGRvIEJyYXNpbCAtIFJGQjEaMBgGA1UEAxMRQUMgU0FGRVdFQiBSRkIgdjUwHhcNMjEwMjA4MTgy\r\n"
		+ "NDQ4WhcNMjIwMjA4MTgyNDQ4WjCCAQMxCzAJBgNVBAYTAkJSMRMwEQYDVQQKEwpJQ1AtQnJhc2ls\r\n"
		+ "MQswCQYDVQQIEwJSUzEVMBMGA1UEBxMMUz9PIExFT1BPTERPMTYwNAYDVQQLEy1TZWNyZXRhcmlh\r\n"
		+ "IGRhIFJlY2VpdGEgRmVkZXJhbCBkbyBCcmFzaWwgLSBSRkIxFjAUBgNVBAsTDVJGQiBlLUNOUEog\r\n"
		+ "QTExFzAVBgNVBAsTDjIyODUzMzAyMDAwMTQ1MRMwEQYDVQQLEwpwcmVzZW5jaWFsMT0wOwYDVQQD\r\n"
		+ "EzRWRVJBIE0gREEgU0lMVkEgUkVJTkhFSU1FUiBFIENJQSBMVERBOjA4ODY1ODEwMDAwMTEyMIIB\r\n"
		+ "IjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArbzstynJ3wj4OJ1P7HM5Hf3yUqcJYTQEdR7p\r\n"
		+ "BR0FUooxrT2Gp+K8KsMcWdGI1HDalDvpiNt6YPcDa7Jr9E0kLHXGxpc0VAm735iIqrcx+CyPkkcQ\r\n"
		+ "N0pFy6KzFVPsEsBriKCSdG+twCDUOzHlmIs6jem3mjlr+JbKi0dd0y7rvtGsibgj0DE9hOS69H60\r\n"
		+ "RTPm8Lqbb6IAfjcORxZe7Hg1HihxRTBpZUlf0dT/jp6NtDB4d/9aZD6YsnOOVJMcj9+NpKDxH0Np\r\n"
		+ "SapoIFkQn35L75Wi+/h8jTfseuUAAzLl+wPSK0pV54TmqWZ/IgmlL0HBSpI2WWGJQfwKOoZdu2dT\r\n"
		+ "EQIDAQABo4ICxzCCAsMwHwYDVR0jBBgwFoAUKV5L1UZMu/4Wp2PBHcQm8t3Y8wUwDgYDVR0PAQH/\r\n"
		+ "BAQDAgXgMG0GA1UdIARmMGQwYgYGYEwBAgEzMFgwVgYIKwYBBQUHAgEWSmh0dHA6Ly9yZXBvc2l0\r\n"
		+ "b3Jpby5hY3NhZmV3ZWIuY29tLmJyL2FjLXNhZmV3ZWJyZmIvYWMtc2FmZXdlYi1yZmItcGMtYTEu\r\n"
		+ "cGRmMIGuBgNVHR8EgaYwgaMwT6BNoEuGSWh0dHA6Ly9yZXBvc2l0b3Jpby5hY3NhZmV3ZWIuY29t\r\n"
		+ "LmJyL2FjLXNhZmV3ZWJyZmIvbGNyLWFjLXNhZmV3ZWJyZmJ2NS5jcmwwUKBOoEyGSmh0dHA6Ly9y\r\n"
		+ "ZXBvc2l0b3JpbzIuYWNzYWZld2ViLmNvbS5ici9hYy1zYWZld2VicmZiL2xjci1hYy1zYWZld2Vi\r\n"
		+ "cmZidjUuY3JsMIGLBggrBgEFBQcBAQR/MH0wUQYIKwYBBQUHMAKGRWh0dHA6Ly9yZXBvc2l0b3Jp\r\n"
		+ "by5hY3NhZmV3ZWIuY29tLmJyL2FjLXNhZmV3ZWJyZmIvYWMtc2FmZXdlYnJmYnY1LnA3YjAoBggr\r\n"
		+ "BgEFBQcwAYYcaHR0cDovL29jc3AuYWNzYWZld2ViLmNvbS5icjCBtwYDVR0RBIGvMIGsgRFGSVND\r\n"
		+ "QUwyQENDLkNOVC5CUqApBgVgTAEDAqAgEx5WRVJBIE1BUklBIERBIFNJTFZBIFJFSU5IRUlNRVKg\r\n"
		+ "GQYFYEwBAwOgEBMOMDg4NjU4MTAwMDAxMTKgOAYFYEwBAwSgLxMtMDYwMzE5NjM0MzI0Njk3NDAw\r\n"
		+ "NDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwoBcGBWBMAQMHoA4TDDAwMDAwMDAwMDAwMDAdBgNV\r\n"
		+ "HSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwCQYDVR0TBAIwADANBgkqhkiG9w0BAQsFAAOCAgEA\r\n"
		+ "YOpBY4tNDWt6GKi9cyFIoUwb800SNrpjVg1BoSymcnLAuRn6HfagrwGffSMwWcMFcjWtCbxe09/4\r\n"
		+ "tnWL7Yi+GiLpZex53YRTwZxbBJVkKmKMbJa3D41slXacKjjav0Aqb7MdP1XEciHJFp8koauiGJBV\r\n"
		+ "turAFDa+c28wMTAtsQWKcr2EYdP1th4QlsTuQe1PQbc4fMbwwbDov384mKjGTC6RvrIaU/UHOriI\r\n"
		+ "lRa8fUsy6rPHsfLdBNY3EUqR4ZT9IZiOZ7gOrseqxhV+oPPPgJs7sEvNPe9QRSLV/VQ6V4c2uiWe\r\n"
		+ "Iq9cdBYNuLw80Vvjvoi04cKA6tTOSaGng1+ETcFsCP8MyR28Pn3zdWMhONdVMaj3cnkwm6FI06HQ\r\n"
		+ "a4Fv2n0cTq06EbAgPDtQX4f3kU3cwHzKIWvij+TNJMPBEjW4sYN4waNpu4BsTWcIWpPLCX179r9N\r\n"
		+ "FFvKZujwMvURv4LWLyySrNuo9AA2PCpMp1GrNuldfrvvxc8Is1eiJFXaXOtANZkJ1No0cfYDIMSX\r\n"
		+ "xP0svKk1oCOLv4Yrbf7bGz15Tge5/yuPWolrMSAym9+dU/5qPQGeL31gk0FxWlWHIlz0XSgIaV2u\r\n"
		+ "yUYdrSI9utGHWdO7weODY15CiRRPz5juQkF6/7A4yR73GPDIKz6RQUsbR37+RPf8Tw2v1/GxnLk=</X509Certificate></X509Data></KeyInfo></Signature></NFe><protNFe versao=\"4.00\"><infProt Id=\"ID143210126829436\"><tpAmb>1</tpAmb><verAplic>3.19.1-149</verAplic><chNFe>43210608865810000112550020000000891000000037</chNFe><dhRecbto>2021-06-25T10:32:27-03:00</dhRecbto><nProt>143210126829436</nProt><digVal>6tbQPla+ylJZ7OlfAmf6U9AjNKs=</digVal><cStat>100</cStat><xMotivo>Autorizado o uso da NF-e</xMotivo></infProt></protNFe></nfeProc>";

public static void main(String[] args) {
		// TODO Auto-generated method stub

		Autorizacao aut = new Autorizacao();
//		System.out.println(aut.confirmaDadosCliente(token, x_integration_key));
//		System.out.println(aut.retornaToken(x_integration_key));
//		System.out.println(aut.retornaChaveIntegracao(token, x_integration_key));
//		System.out.println(aut.enviaXML(token, x_integration_key));
//		System.out.println(aut.enviaXml(token,x_integration_key,arquivo));
		
		ServicoConfig s = new ServicoConfig();
		s.trataConfig();
		
		EnviaXML gera = new EnviaXML();
		gera.geraNotaEntrada();
		
		System.out.println(aut.retornaToken(Config.x_integration_key, Config.client_id, Config.client_secret, Config.audience));
		
//		Arquivo arquivo = new Arquivo();
//		List<String> arquivos = arquivo.listarArquivosXML(dirNFCe);
////		arquivo.deletarArquivo();
//		int val = 0;
//		for(int i =0; i < arquivos.size(); i++) {
//			System.out.println(arquivos.get(i) );
//			val=val+1;
//		}
//		System.out.println(val);
//		
//		System.out.println(aut.retornaToken(Config.x_integration_key, Config.client_id, Config.client_secret, Config.audience));
		
//		 
	}

	
	//https://www.mballem.com/post/manipulando-arquivo-txt-com-java/

}
