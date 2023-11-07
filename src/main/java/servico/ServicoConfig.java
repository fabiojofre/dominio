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
		

		Config.x_integration_key = VrProperties.getString("config.chave");
		Config.client_id = VrProperties.getString("config.client_id");
		Config.client_secret = VrProperties.getString("config.client_secret");
		Config.audience = VrProperties.getString("config.audience");
		Config.diretorio = VrProperties.getString("config.diretorio");
		

		String dadosProp ="";
		int cont = 0;
		if(Config.x_integration_key  == null) {
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
			String sql = "select token from dominio.token limit 1";
			PreparedStatement stmt = cs.prepareStatement(sql);
//			stmt.setString(1, Config.token);
			ResultSet rs = stmt.executeQuery();

			
			while (rs.next()) {
				Config.token = rs.getString(1);
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
		System.out.println("ID Cliente: " + Config.client_id);
		System.out.println("Secret Cliente: " + Config.client_secret);
		System.out.println("Audience: " + Config.audience);
		System.out.println("Token Temporário: "+Config.token);
		System.out.println("Pasta de XMLs: "+Config.diretorio);
	}
}
