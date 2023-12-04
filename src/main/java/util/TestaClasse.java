package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.swing.JOptionPane;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import servico.Arquivo;
import servico.Autorizacao;
import servico.Config;
import servico.EnviaXML;
import servico.ServicoConfig;
import vrrecifeframework.classes.VrProperties;

public class TestaClasse {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String teste = JOptionPane.showInputDialog("Digite a chave de integração fornecida pela contabilidade");
	
		JOptionPane.showMessageDialog(null, teste);
	}
}
