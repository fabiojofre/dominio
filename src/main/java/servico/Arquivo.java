package servico;

import java.io.File;
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
		System.out.println(file.delete());
	}
	

}
