package servico;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import conexao.ConexaoServidor;
import util.Util;
import vrrecifeframework.classes.VrProperties;

public class EnviaXML {

	private String NOTASAIDA = "SELECT nsd.id,nsd.id_situacaonfe,nsd.id_notasaida, nsd.xml,chavenfe FROM notasaidanfe nsd join notasaida ns on ns.id = nsd.id_notasaida WHERE nsd.id_situacaonfe = 1 AND (nsd.cofre = 0 OR nsd.cofre is null) ORDER BY 1 DESC LIMIT 300";
	private String UPDATE_NOTASAIDA = "update notasaidanfe set cofre = 1 where id = ?";

	private String NOTAENTRADA = "SELECT id, id_situacaonfe,numeronota, xml, chavenfe FROM notaentradanfe WHERE id_situacaonfe = 1 AND (cofre = 0 OR cofre is null) ORDER BY 1 DESC LIMIT 300";
	private String UPDATE_NOTAENTRADA = "update notaentradanfe set cofre = 1 where id = ?";

	private String NFCE = "SELECT id, id_situacaonfce,id_venda, xml, chavenfce FROM pdv.vendanfce WHERE id_situacaonfce = 1 AND (cofre = 0 OR cofre is null) ORDER BY 1 DESC LIMIT 300";
	private String UPDATE_NFCE = "update pdv.vendanfce set cofre = 1 where id = ?";

	ConexaoServidor con = new ConexaoServidor();

	PreparedStatement stmt = null;

	public static String mensagemSaida = "";

	public void geraNotaSaida() {
		try {
			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NOTASAIDA);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTASAIDA);
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while (rs.next()) {
				String xml = rs.getString("xml");
//				int inicio = xml.indexOf("infNFe Id=") + 10;
//				int fim = xml.indexOf(" versao=", inicio);
				String nomeArquivo = Config.diretorio + "NFe" + rs.getString("chavenfe") + ".xml";	 // tirar
																													// as
																													// aspas
				Arquivo ar = new Arquivo();
				ar.geraArquivo(nomeArquivo, xml);
//					verificar aquivo gerado
				File arq = new File(nomeArquivo);
				if (arq.exists()) {
					System.out.println("Arquivo: " + nomeArquivo + " encontrado na pasta");
					stmt_up.setInt(1, rs.getInt("id"));
					int rowsInserted = stmt_up.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Update executado!");
					} else {
						System.out.println("Update Falhou!");
					}
					cont++;
				} else {
					System.out.println("Retorno: Arquivo não encontrado!");
				}

			}
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);

		}
	}

	public void geraCupom() {
		try {
			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NFCE);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NFCE);
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while (rs.next()) {

				String xml = rs.getString("xml");
				String nomeArquivo = Config.diretorio + "NFCe" + rs.getString("chavenfce") + ".xml";

				Arquivo ar = new Arquivo();
				ar.geraArquivo(nomeArquivo, xml);
//				verificar aquivo gerado
				File arq = new File(nomeArquivo);
				if (arq.exists()) {
					System.out.println("Arquivo: " + nomeArquivo + " encontrado na pasta");
					stmt_up.setInt(1, rs.getInt("id"));
					int rowsInserted = stmt_up.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Update executado!");
					} else {
						System.out.println("Update Falhou!");
					}
					cont++;
				} else {
					System.out.println("Retorno: Arquivo não encontrado!");
				}

			}
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}

	}

	public void geraNotaEntrada() {
		try {

			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NOTAENTRADA);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTAENTRADA);
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while (rs.next()) {

				String xml = rs.getString("xml");
				String nomeArquivo = Config.diretorio + "NFe" + rs.getString("chavenfe") + ".xml";

				Arquivo ar = new Arquivo();
				ar.geraArquivo(nomeArquivo, xml);
//				verificar aquivo gerado
				File arq = new File(nomeArquivo);
				if (arq.exists()) {
					System.out.println("Arquivo: " + nomeArquivo + " encontrado na pasta");
					stmt_up.setInt(1, rs.getInt("id"));
					int rowsInserted = stmt_up.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Update executado!");
					} else {
						System.out.println("Update Falhou!");
					}
					cont++;
				} else {
					System.out.println("Retorno: Arquivo não encontrado!");
				}

			}
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		

	}

}
