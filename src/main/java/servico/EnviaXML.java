package servico;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.GregorianCalendar;

import conexao.ConexaoServidor;
import dominio.Retorno;

public class EnviaXML {

	private String NOTASAIDA = "SELECT nsd.id,nsd.id_situacaonfe,nsd.id_notasaida, nsd.xml,chavenfe FROM notasaidanfe nsd join notasaida ns on ns.id = nsd.id_notasaida WHERE nsd.id_situacaonfe = 1 AND (nsd.cofre = 0 OR nsd.cofre is null)AND ns.id_loja = ? and ns.datasaida >= ? ORDER BY 1 DESC LIMIT 10";
	private String UPDATE_NOTASAIDA = "update notasaidanfe set cofre = 1 where id = ?";
	private String UPDATE_NOTASAIDA_ERRO_XML = "update notasaidanfe set cofre = 2 where id = ?";

	private String NOTAENTRADA = "SELECT id, id_situacaonfe,numeronota, xml, chavenfe FROM notaentradanfe WHERE id_situacaonfe = 1 AND (cofre = 0 OR cofre is null) AND id_loja = ? AND dataentrada::date >= ? ORDER BY 1 DESC LIMIT 10";
	private String UPDATE_NOTAENTRADA = "update notaentradanfe set cofre = 1 where id = ?";
	private String UPDATE_NOTAENTRADA_ERRO_XML = "update notaentradanfe set cofre = 2 where id = ?";

	private String NFCE = "SELECT v.id, v.id_situacaonfce,v.id_venda, v.xml, v.chavenfce FROM pdv.vendanfce v join pdv.venda pv on v.id_venda = pv.id  WHERE v.id_situacaonfce = 1 AND (v.cofre = 0 OR v.cofre is null) and pv.id_loja = ? and pv.data >= ? ORDER BY 1 DESC LIMIT 10";
	private String UPDATE_NFCE = "update pdv.vendanfce set cofre = 1 where id = ?";
	private String UPDATE_NFCE_ERRO_XML = "update pdv.vendanfce set cofre = 2 where id = ?";

	ConexaoServidor con = new ConexaoServidor();

	PreparedStatement stmt = null;

	public static String mensagemSaida = "";
	
	public void geraNotaSaida() {
		int cont = 0;
		System.out.println("Gerando notas de Sa�da:");
		try {
			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NOTASAIDA);
			stmt.setInt(1, Config.loja);
			stmt.setDate(2, Config.inicio);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTASAIDA);
			PreparedStatement stmt_up_erro_xml = con.prepareStatement(UPDATE_NOTASAIDA_ERRO_XML);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Autorizacao aut = new Autorizacao();
				String xml = rs.getString("xml");
				String nomeArquivo = Config.diretorio + "NFe" + rs.getString("chavenfe") + ".xml"; // tirar
																									// as
				 // aspas
				Arquivo ar = new Arquivo();
				Arquivo arquivo = new Arquivo();
				ar.geraArquivo(nomeArquivo, xml);
//					verificar aquivo gerado
				File arq = new File(nomeArquivo);

				if (arq.exists()) { // se o arquivo foi gerado normalmente
					System.out.println("Arquivo: " + nomeArquivo + " encontrado na pasta");
					Retorno retornoEnvio = new Retorno();
					retornoEnvio = aut.enviaXml(Config.token, Config.x_integration_key, nomeArquivo); // envia o arquivo
																										// .xml
					 if (retornoEnvio.getStatus().getCode().equals("E2")||
							 retornoEnvio.getStatus().getCode().equals("EA0")||
							 retornoEnvio.getStatus().getCode().equals("EA3") ) {
						System.out.println(retornoEnvio.getStatus().getMessage());
						GregorianCalendar g = new GregorianCalendar();
						arquivo.escreverLog("Aqruivo: "+nomeArquivo+", Mensagem retorno: "+retornoEnvio.getStatus().getMessage()+" - Data: "+g.getTime());
						System.out.println("Tentando deletar o arquivo ...");
						if (arq.delete()) {
							System.out.println("deletado!!!");
						}
					}
					else if (retornoEnvio.getStatus().getCode().equals("TO")) { // se o thoken expirar ele gera outro e apaga
								System.out.println("Precisou gerar outro token...");											// o arquivo
						Config.token = aut.retornaToken(Config.x_integration_key, Config.client_id,
								Config.client_secret, Config.audience);
						System.out.println("Tentando deletar o arquivo ...");
						if (arq.delete()) {
							System.out.println("deletado!!!");
						}
					} else if (retornoEnvio.getStatus().getCode().equals("S1")) { // se fou recebido corretamente
						System.out.println("Status envio do XML - "+retornoEnvio.getStatus().getMessage()+": "+retornoEnvio.getStatus().getMessage());
						Retorno ret = new Retorno();
						System.out.println("Confirmando envio com id: "+retornoEnvio.getId());
						ret = aut.confirmaProcessamento(Config.token, Config.x_integration_key, retornoEnvio.getId());// envia a chave pra confirma��o
						String status = "Codigo NFe: " + rs.getInt("id") + ", Id_Envio: "+retornoEnvio.getId()+", Chave: "+rs.getString("chavenfe")+", retornou: "+ret.getFilesExpanded().getApiStatus().getCode()+": "+ret.getFilesExpanded().getApiStatus().getMessage();
						System.out.println(status);
						while (ret.getFilesExpanded().getApiStatus().getCode().equals("SA1")) { // enquanto esse xml n�o for processado ele continua enviando
							System.out.println("Nova tentativa de confirma��o de processamento...");
							ret = aut.confirmaProcessamento(Config.token, Config.x_integration_key, retornoEnvio.getId()); 
							System.out.println(status);
						}
						if (ret.getFilesExpanded().getApiStatus().getCode().equals("SA2")||ret.getFilesExpanded().getApiStatus().getCode().equals("EA10")) { // se estiver tudo certo d� update pra cofre = 1 e deleta o arquivo
							GregorianCalendar g = new GregorianCalendar();
							arquivo.escreverLog(status+" - Data: "+g.getTime());
							stmt_up.setInt(1, rs.getInt("id"));
							int rowsInserted = stmt_up.executeUpdate();
							if (rowsInserted > 0) {
								System.out.println("Update executado!");
								System.out.println("Tentando deletar o arquivo ...");
								if (arq.delete()) {
									System.out.println("deletado!!!");
								}
							} else {
								System.out.println("Update Falhou!");
							}
							cont++;
						}
					}else {
						GregorianCalendar g = new GregorianCalendar();
						arquivo.escreverLog("Aqruivo: "+nomeArquivo+", Mensagem retorno: "+retornoEnvio.getStatus().getMessage()+" - Data: "+g.getTime());
						stmt_up_erro_xml.setInt(1, rs.getInt("id"));
						int rowsInserted = stmt_up_erro_xml.executeUpdate();
						if (rowsInserted > 0) {
							System.out.println("Update executado!");
							System.out.println("Tentando deletar o arquivo ...");
							if (arq.delete()) {
								System.out.println("deletado!!!");
							}
						} else {
							System.out.println("Update Falhou!");
						}
					}
					
				} else {
					System.out.println("Retorno: Arquivo n�o encontrado!");
				}

			}
			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);

		}
		System.out.println(cont + " Notas de sa�da encontradas e geradas \n \n");
	}

	public void geraCupom() {
		int cont = 0;
		System.out.println("Gerando cupons:");
		try {
			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NFCE);
			stmt.setInt(1, Config.loja);
			stmt.setDate(2, Config.inicio);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NFCE);
			PreparedStatement stmt_up_erro_xml = con.prepareStatement(UPDATE_NFCE_ERRO_XML);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Autorizacao aut = new Autorizacao();
				
				String xml = rs.getString("xml");
				String nomeArquivo = Config.diretorio + "NFCe" + rs.getString("chavenfce") + ".xml";
				
				Arquivo ar = new Arquivo();
				Arquivo arquivo = new Arquivo();
				ar.geraArquivo(nomeArquivo, xml);
//				verificar aquivo gerado
				File arq = new File(nomeArquivo);
				if (arq.exists()) {
					System.out.println("Arquivo: " + nomeArquivo + " encontrado na pasta");
					Retorno retornoEnvio = new Retorno();
					retornoEnvio = aut.enviaXml(Config.token, Config.x_integration_key, nomeArquivo); // envia o arquivo
																										// .xml
					 if (retornoEnvio.getStatus().getCode().equals("E2")||
							 retornoEnvio.getStatus().getCode().equals("EA0")||
							 retornoEnvio.getStatus().getCode().equals("EA3") ) {
						System.out.println(retornoEnvio.getStatus().getMessage());
						GregorianCalendar g = new GregorianCalendar();
						arquivo.escreverLog("Aqruivo: "+nomeArquivo+", Mensagem retorno: "+retornoEnvio.getStatus().getMessage()+" - Data: "+g.getTime());
						System.out.println("Tentando deletar o arquivo ...");
						if (arq.delete()) {
							System.out.println("deletado!!!");
						}
					}
					else if (retornoEnvio.getStatus().getCode().equals("TO")) { // se o thoken expirar ele gera outro e apaga
								System.out.println("Precisou gerar outro token...");											// o arquivo
						Config.token = aut.retornaToken(Config.x_integration_key, Config.client_id,
								Config.client_secret, Config.audience);
						System.out.println("Tentando deletar o arquivo ...");
						if (arq.delete()) {
							System.out.println("deletado!!!");
						}
					} else if (retornoEnvio.getStatus().getCode().equals("S1")) { // se fou recebido corretamente
						System.out.println("Status envio do XML - "+retornoEnvio.getStatus().getMessage()+": "+retornoEnvio.getStatus().getMessage());
						Retorno ret = new Retorno();
						System.out.println("Confirmando envio com id: "+retornoEnvio.getId());
						ret = aut.confirmaProcessamento(Config.token, Config.x_integration_key, retornoEnvio.getId());// envia a chave pra confirma��o
						String status = "Codigo NFc-e: " + rs.getInt("id")+ ", Id_Envio: "+retornoEnvio.getId() + ", Chave: "+rs.getString("chavenfce")+", retornou: "+ret.getFilesExpanded().getApiStatus().getCode()+": "+ret.getFilesExpanded().getApiStatus().getMessage();
						System.out.println(status);
						while (ret.getFilesExpanded().getApiStatus().getCode().equals("SA1")) { // enquanto esse xml n�o for processado ele continua enviando
							System.out.println("Nova tentativa de confirma��o de processamento...");
							ret = aut.confirmaProcessamento(Config.token, Config.x_integration_key, retornoEnvio.getId()); 
							System.out.println(status);
						}
						if (ret.getFilesExpanded().getApiStatus().getCode().equals("SA2")||ret.getFilesExpanded().getApiStatus().getCode().equals("EA10")) { // se estiver tudo certo d� update pra cofre = 1 e deleta o arquivo
							GregorianCalendar g = new GregorianCalendar();
							arquivo.escreverLog(status+" - Data: "+g.getTime());
							stmt_up.setInt(1, rs.getInt("id"));
							int rowsInserted = stmt_up.executeUpdate();
							if (rowsInserted > 0) {
								System.out.println("Update executado!");
								System.out.println("Tentando deletar o arquivo ...");
								if (arq.delete()) {
									System.out.println("deletado!!!");
								}
							} else {
								System.out.println("Update Falhou!");
							}
							cont++;
						}
					}else {
						GregorianCalendar g = new GregorianCalendar();
						arquivo.escreverLog("Aqruivo: "+nomeArquivo+", Mensagem retorno: "+retornoEnvio.getStatus().getMessage()+" - Data: "+g.getTime());
						stmt_up_erro_xml.setInt(1, rs.getInt("id"));
						int rowsInserted = stmt_up_erro_xml.executeUpdate();
						if (rowsInserted > 0) {
							System.out.println("Update executado!");
							System.out.println("Tentando deletar o arquivo ...");
							if (arq.delete()) {
								System.out.println("deletado!!!");
							}
						} else {
							System.out.println("Update Falhou!");
						}
					}
					
				} else {
					System.out.println("Retorno: Arquivo n�o encontrado!");
				}
			}

			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		System.out.println(cont + " cupons encontrados e gerados");

	}
	public void geraNotaEntrada() {
		int cont = 0;
		System.out.println("Gerando notas de Entrada:");
		try {

			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NOTAENTRADA);
			stmt.setInt(1, Config.loja);
			stmt.setDate(2, Config.inicio);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTAENTRADA);
			PreparedStatement stmt_up_erro_xml = con.prepareStatement(UPDATE_NOTAENTRADA_ERRO_XML);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Autorizacao aut = new Autorizacao();
				String xml = rs.getString("xml");
				String nomeArquivo = Config.diretorio + "NFee" + rs.getString("chavenfe") + ".xml";

				Arquivo ar = new Arquivo();
				Arquivo arquivo = new Arquivo();
				ar.geraArquivo(nomeArquivo, xml);
//				verificar aquivo gerado
				File arq = new File(nomeArquivo);
				if (arq.exists()) {
					System.out.println("Arquivo: " + nomeArquivo + " encontrado na pasta");
					Retorno retornoEnvio = new Retorno();
					retornoEnvio = aut.enviaXml(Config.token, Config.x_integration_key, nomeArquivo); // envia o arquivo
																										// .xml
					 if (retornoEnvio.getStatus().getCode().equals("E2")||
							 retornoEnvio.getStatus().getCode().equals("EA0")||
							 retornoEnvio.getStatus().getCode().equals("EA3") ) {
						System.out.println(retornoEnvio.getStatus().getMessage());
						GregorianCalendar g = new GregorianCalendar();
						arquivo.escreverLog("Aqruivo: "+nomeArquivo+", Mensagem retorno: "+retornoEnvio.getStatus().getMessage()+" - Data: "+g.getTime());
						System.out.println("Tentando deletar o arquivo ...");
						if (arq.delete()) {
							System.out.println("deletado!!!");
						}
					}
					else if (retornoEnvio.getStatus().getCode().equals("TO")) { // se o thoken expirar ele gera outro e apaga
								System.out.println("Precisou gerar outro token...");											// o arquivo
						Config.token = aut.retornaToken(Config.x_integration_key, Config.client_id,
								Config.client_secret, Config.audience);
						System.out.println("Tentando deletar o arquivo ...");
						if (arq.delete()) {
							System.out.println("deletado!!!");
						}
					} else if (retornoEnvio.getStatus().getCode().equals("S1")) { // se fou recebido corretamente
						System.out.println("Status envio do XML - "+retornoEnvio.getStatus().getMessage()+": "+retornoEnvio.getStatus().getMessage());
						Retorno ret = new Retorno();
						System.out.println("Confirmando envio com id: "+retornoEnvio.getId());
						ret = aut.confirmaProcessamento(Config.token, Config.x_integration_key, retornoEnvio.getId());// envia a chave pra confirma��o
						String status = "Codigo NFee: " + rs.getInt("id")+ ", Id_Envio: "+retornoEnvio.getId() + ", Chave: "+rs.getString("chavenfe")+", retornou: "+ret.getFilesExpanded().getApiStatus().getCode()+": "+ret.getFilesExpanded().getApiStatus().getMessage();
						System.out.println(status);
						while (ret.getFilesExpanded().getApiStatus().getCode().equals("SA1")) { // enquanto esse xml n�o for processado ele continua enviando
							System.out.println("Nova tentativa de confirma��o de processamento...");
							ret = aut.confirmaProcessamento(Config.token, Config.x_integration_key, retornoEnvio.getId()); 
							System.out.println(status);
						}
						if (ret.getFilesExpanded().getApiStatus().getCode().equals("SA2")||ret.getFilesExpanded().getApiStatus().getCode().equals("EA10")) { // se estiver tudo certo d� update pra cofre = 1 e deleta o arquivo
							GregorianCalendar g = new GregorianCalendar();
							arquivo.escreverLog(status+" - Data: "+g.getTime());
							stmt_up.setInt(1, rs.getInt("id"));
							int rowsInserted = stmt_up.executeUpdate();
							if (rowsInserted > 0) {
								System.out.println("Update executado!");
								System.out.println("Tentando deletar o arquivo ...");
								if (arq.delete()) {
									System.out.println("deletado!!!");
								}
							} else {
								System.out.println("Update Falhou!");
							}
							cont++;
						}
					}else {
						GregorianCalendar g = new GregorianCalendar();
						arquivo.escreverLog("Aqruivo: "+nomeArquivo+", Mensagem retorno: "+retornoEnvio.getStatus().getMessage()+" - Data: "+g.getTime());
						stmt_up_erro_xml.setInt(1, rs.getInt("id"));
						int rowsInserted = stmt_up_erro_xml.executeUpdate();
						if (rowsInserted > 0) {
							System.out.println("Update executado!");
							System.out.println("Tentando deletar o arquivo ...");
							if (arq.delete()) {
								System.out.println("deletado!!!");
							}
						} else {
							System.out.println("Update Falhou!");
						}
					}
					
				} else {
					System.out.println("Retorno: Arquivo n�o encontrado!");
				}

			}
			System.out.println(cont + " arquivos encontrados");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}

	}

}
