package servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import conexao.ConexaoServidor;
import vrrecifeframework.classes.VrProperties;

public class ServicoConfig {

	private void lerProperties() {
		
		Config.host = VrProperties.getString("database.ip");
		Config.base = VrProperties.getString("database.nome");
		Config.porta = VrProperties.getInt("database.porta");
		Config.usuario = VrProperties.getString("database.usuario");
		Config.senha = VrProperties.getString("database.senha");
		Config.loja = VrProperties.getInt("config.loja");

		Config.client_id = VrProperties.getString("config.client_id");
		Config.client_secret = VrProperties.getString("config.client_secret");
		Config.audience = VrProperties.getString("config.audience");
		Config.chave_temporaria = VrProperties.getString("config.integration_key");
		
		

		String dadosProp ="";
		int cont = 0;
		if(Config.chave_temporaria  == null) {
			dadosProp = dadosProp+"\n config.chave";
			cont=cont+1;
		}
		if(Config.client_id == null) {
			dadosProp = dadosProp+"\n config.client_id ";
			cont=cont+1;
		}
		if(Config.client_secret == null) {
			dadosProp = dadosProp+"\n config.client_secret ";
			cont=cont+1;
		}
		if(Config.audience == null) {
			dadosProp = dadosProp+"\n config.audience";
			cont=cont+1;
		}
		if(Config.loja == null) {
			dadosProp = dadosProp+"\n config.loja";
			cont=cont+1;
		}
		if(cont >0) {
			JOptionPane.showMessageDialog(null, "Verifique se os dados abaixo estao inseridos corretamnte no config.properties.\n"+dadosProp+"","Erro de configuracao!",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void trataConfig() {
		lerProperties();
		ConexaoServidor cs = new ConexaoServidor();

		try {
			cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
			System.out.println("Conexao inicial bem sucedida!");
			String sql = "select token, chave, inicio from dominio_api.token where id_loja = ? limit 1";
			PreparedStatement stmt = cs.prepareStatement(sql);
			stmt.setInt(1, Config.loja);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Config.token = rs.getString(1);
				Config.x_integration_key = rs.getString(2);
				Config.inicio = rs.getDate(3);
			}
			

			String sql2 = "select l.id, f.nomefantasia, cnpj from loja l join fornecedor f on l.id_fornecedor = f.id where l.id = ?";
			PreparedStatement stmt2 = cs.prepareStatement(sql2);
			stmt2.setInt(1, Config.loja);
			ResultSet rs2 = stmt2.executeQuery();
			while (rs2.next()) {
				Config.loja = rs2.getInt(1);
				Config.nomeLoja = rs2.getString(2);
				Config.cnpj = rs2.getLong(3);
			}
			cs.close();
		} catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Erro ao conectar no banco de dados!", "Erro configuracao VR.",
					JOptionPane.ERROR_MESSAGE);
		}

		System.out.println("\nOs dados carregados sao: ");
		System.out.println("\n**** Dados da conexao: **** ");
		System.out.println("Servidor: " + Config.host);
		System.out.println("Banco: " + Config.base);
		System.out.println("Porta: " + Config.porta);
		System.out.println("Usuario: " + Config.usuario);
		System.out.println("Senha: " + Config.senha);

		System.out.println("\n**** Dominio: **** ");
		System.out.println("Chave: " + Config.x_integration_key);
		System.out.println("Chave Temporaria: " + Config.chave_temporaria);
		System.out.println("ID Cliente: " + Config.client_id);
		System.out.println("Secret Cliente: " + Config.client_secret);
		System.out.println("Audience: " + Config.audience);
		System.out.println("Token Temporário: "+Config.token);
		System.out.println("Pasta de XMLs: "+Config.diretorio);
		System.out.println("Id da Loja: "+Config.loja);
		System.out.println("Nome Loja: "+Config.nomeLoja);
		System.out.println("CNPJ: "+Config.cnpj);
		System.out.println("Data de inicio: "+Config.inicio);
		System.out.println("**********************************\n");
	}
}
