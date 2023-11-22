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

	private String NOTASAIDA = "SELECT nsd.id,nsd.id_situacaonfe,nsd.id_notasaida, nsd.xml,chavenfe FROM notasaidanfe nsd join notasaida ns on ns.id = nsd.id_notasaida WHERE nsd.id_situacaonfe = 1 AND (nsd.cofre = 0 OR nsd.cofre is null)AND ns.id_loja = ? ORDER BY 1 DESC LIMIT 30";
	private String UPDATE_NOTASAIDA = "update notasaidanfe set cofre = 1 where id = ?";

	private String NOTAENTRADA = "SELECT id, id_situacaonfe,numeronota, xml, chavenfe FROM notaentradanfe WHERE id_situacaonfe = 1 AND (cofre = 0 OR cofre is null) AND id_loja = ? ORDER BY 1 DESC LIMIT 30";
	private String UPDATE_NOTAENTRADA = "update notaentradanfe set cofre = 1 where id = ?";

	private String NFCE = "SELECT v.id, v.id_situacaonfce,v.id_venda, v.xml, v.chavenfce FROM pdv.vendanfce v join pdv.venda pv on v.id_venda = pv.id  WHERE v.id_situacaonfce = 1 AND (v.cofre = 0 OR v.cofre is null) and pv.id_loja = ?  ORDER BY 1 DESC LIMIT 30";
	private String UPDATE_NFCE = "update pdv.vendanfce set cofre = 1 where id = ?";

	ConexaoServidor con = new ConexaoServidor();

	PreparedStatement stmt = null;

	public static String mensagemSaida = "";

	public void geraNotaSaida() {
		try {
			con.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);

			stmt = con.prepareStatement(NOTASAIDA);
			stmt.setInt(1, Config.loja);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTASAIDA);
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while (rs.next()) {
				String xml = rs.getString("xml");
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
			stmt.setInt(1, Config.loja);
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
			stmt.setInt(1, Config.loja);
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
