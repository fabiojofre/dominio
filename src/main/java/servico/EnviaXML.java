package servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import conexao.ConexaoServidor;
import util.Util;
import vrrecifeframework.classes.VrProperties;
public class EnviaXML {
	
	private String NOTASAIDA = "SELECT id, id_situacaonfe,id_notasaida, xml FROM notasaidanfe WHERE id_situacaonfe = 1 AND (cofre = 0 OR cofre is null) ORDER BY 1 LIMIT 10";
	private String UPDATE_NOTASAIDA = "update notasaidanfe set cofre = 1 where id = ?";
	
	private String NOTAENTRADA = "SELECT id, id_situacaonfe,numeronota, xml FROM notaentradanfe WHERE id_situacaonfe = 1 AND (cofre = 0 OR cofre is null) ORDER BY 1 LIMIT 10";
	private String UPDATE_NOTAENTRADA = "update notaentradanfe set cofre = 1 where id = ?";
	
	private String NFCE ="SELECT id, id_situacaonfce,id_venda, xml FROM pdv.vendanfce WHERE id_situacaonfce = 1 AND (cofre = 0 OR cofre is null) ORDER BY 1 LIMIT 10";
	private String UPDATE_NFCE = "update pdv.vendanfce set cofre = 1 where id = ?";
		
	ConexaoServidor con = new ConexaoServidor();
	
		PreparedStatement stmt = null;
		
		public static String mensagemSaida="";
	
				
	public void enviaNotaSaida() {
		try {
			con.abrirConexao(VrProperties.getString("database.ip"), VrProperties.getInt("database.porta"), VrProperties.getString("database.nome"), "postgres", "VrPost@Server");
			String email = VrProperties.getString("sieg.email");
			String apikey = VrProperties.getString("sieg.apikey");
			Servico servico = new Servico();
			
			
			stmt = con.prepareStatement(NOTASAIDA);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTASAIDA);
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while(rs.next()){
			
			JSONObject msg = servico.enviaXML(apikey, email, rs.getString("xml"));	
			Util u = new Util();
			String mensagem = u.trataToken(msg);
			if(mensagem.equals("Importado com sucesso")) {
				System.out.println("Nota: "+rs.getInt("id_notasaida")+" "+mensagem);
				
				stmt_up.setInt(1, rs.getInt("id"));
				int rowsInserted = stmt_up.executeUpdate(); 
				 if (rowsInserted > 0) {
				     System.out.println("Update executado!");
				 }else {
					 System.out.println("Update Falhou!");
				 }
				 cont ++;
			}else {
				System.out.println("Retorno: "+mensagem);
			}
				
			}
			if(cont >0) {
			mensagemSaida = cont+" Notas Fiscais importadas!";
			System.out.println(mensagemSaida);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.toString().contains("not found")) {
				JOptionPane.showMessageDialog(null, "Verifique se os dados dos parâmetros estão corretos!",  "Erro no retorno do WebService!",JOptionPane.ERROR_MESSAGE );
				System.out.println(e);
				System.exit(0);	
			}else 
				System.out.println("WebService temporarialente fora!");
		}
	}
		
	public void enviaCupom() {
			try {
				con.abrirConexao(VrProperties.getString("database.ip"), VrProperties.getInt("database.porta"), VrProperties.getString("database.nome"), "postgres", "VrPost@Server");
				String email = VrProperties.getString("sieg.email");
				String apikey = VrProperties.getString("sieg.apikey");
				Servico servico = new Servico();
				
				
				stmt = con.prepareStatement(NFCE);
				PreparedStatement stmt_up = con.prepareStatement(UPDATE_NFCE);
				ResultSet rs = stmt.executeQuery();
				int cont = 0;
				while(rs.next()){
				
				JSONObject msg = servico.enviaXML(apikey, email, rs.getString("xml"));	
				Util u = new Util();
				String mensagem = u.trataToken(msg);
				if(mensagem.equals("Importado com sucesso")) {
					System.out.println("Cupom: "+rs.getInt("id_venda")+" "+mensagem);
					
					stmt_up.setInt(1, rs.getInt("id"));
					int rowsInserted = stmt_up.executeUpdate(); 
					 if (rowsInserted > 0) {
					     System.out.println("Update executado!");
					 }else {
						 System.out.println("Update Falhou!");
					 }
					 cont ++;
				}else {
					System.out.println("Retorno: "+mensagem);
				}
					
				}
				if(cont >0) {
				mensagemSaida = cont+" Cupons Fiscais importadas!";
				System.out.println(mensagemSaida);
				}
				
				
			} catch (Exception e)  {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(e.toString().contains("not found")) {
					JOptionPane.showMessageDialog(null, "Verifique se os dados dos parâmetros estão corretos!",  "Erro no retorno do WebService!",JOptionPane.ERROR_MESSAGE );
					System.out.println(e);
					System.exit(0);	
				}else 
					System.out.println("WebService temporarialente fora!");
			}
		
	}
	
	public void enviaNotaEntrada() {
		try {
			con.abrirConexao(VrProperties.getString("database.ip"), VrProperties.getInt("database.porta"), VrProperties.getString("database.nome"), "postgres", "VrPost@Server");
			String email = VrProperties.getString("sieg.email");
			String apikey = VrProperties.getString("sieg.apikey");
			Servico servico = new Servico();
			
			
			stmt = con.prepareStatement(NOTAENTRADA);
			PreparedStatement stmt_up = con.prepareStatement(UPDATE_NOTAENTRADA);
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while(rs.next()){
			
			JSONObject msg = servico.enviaXML(apikey, email, rs.getString("xml"));	
			Util u = new Util();
			String mensagem = u.trataToken(msg);
			if(mensagem.equals("Importado com sucesso")) {
				System.out.println("Nota de Entrada: "+rs.getInt("numeronota")+" "+mensagem);
				
				stmt_up.setInt(1, rs.getInt("id"));
				int rowsInserted = stmt_up.executeUpdate(); 
				 if (rowsInserted > 0) {
				     System.out.println("Update executado!");
				 }else {
					 System.out.println("Update Falhou!");
				 }
				 cont ++;
			}else {
				System.out.println("Retorno: "+mensagem);
			}
				
			}
			if(cont >0) {
			mensagemSaida = cont+" Notas Fiscais importadas!";
			System.out.println(mensagemSaida);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.toString().contains("not found")) {
				JOptionPane.showMessageDialog(null, "Verifique se os dados dos parâmetros estão corretos!",  "Erro no retorno do WebService!",JOptionPane.ERROR_MESSAGE );
				System.out.println(e);
				System.exit(0);	
			}else 
				System.out.println("WebService temporarialente fora!");
		}
	}
	
	

}
