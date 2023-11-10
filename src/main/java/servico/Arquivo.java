package servico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
	
	public String diretorio;
	
	public List<String> listarArquivosXML(String diretorio) {
		File dir = new File(diretorio);
		List<String> arquivos = new ArrayList<String>();
		for(File file:dir.listFiles()) {
			if(file.getName().endsWith("xml") || file.getName().endsWith("XML")) {
//			System.out.println(dir+"\\"+file.getName());
			arquivos.add(dir+"\\"+file.getName());
			}
		}
		return arquivos;
	}
	
	public void deletarArquivo(String arquivo) {
		File file = new File(arquivo);
		System.out.println("Arquivo "+arquivo+" excluído com sucesso!");
	}
	
	public void geraArquivo(String arquivo, String xml) {
		File file = new File(arquivo);
		try {
			file.createNewFile();
			 FileWriter fileWriter = new FileWriter(arquivo, false);
	         PrintWriter printWriter = new PrintWriter(fileWriter);
	         printWriter.print(xml);
	         printWriter.flush();
	            printWriter.close();
			System.out.println("XML "+arquivo+" gerado com sucesso");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro na geracao do arquivo "+arquivo);
			e.printStackTrace();
		}
		
	}
}
