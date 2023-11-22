package tela;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import servico.Arquivo;
import servico.Autorizacao;
import servico.Config;
import servico.EnviaXML;
import servico.ServicoConfig;
import util.Autenticacao;


public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;
	
	EnviaXML envia = new EnviaXML();

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	Principal frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					ServicoConfig serv = new ServicoConfig();
					serv.trataConfig();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JButton btnIniciar = new JButton("Iniciar");
	JButton btnParar = new JButton("Parar");
	JButton btnSair = new JButton("Sair");
	JLabel lbStatus = new JLabel("");

	// variaveis para o timer
	Timer timer;
	Timer timer2;
	boolean atividade = false;
	// variavel properties
//	ServicoConfig serv = new ServicoConfig();
	int minutos;
	private final JLabel lbStatus_1 = new JLabel("");

	/**
	 * Create the frame.
	 */
	public Principal() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/vrrecifeframework/img/cofre.png")));
		minutos = 2;
		setResizable(false);
		setAutoRequestFocus(false);
		setTitle("Dominio APP - ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 402, 165);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JDesktopPane desktopPane = new JDesktopPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Servicos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(114, 0, 178, 28);
		desktopPane.add(lblNewLabel);
		
		//------ O Icone na bandeja do rel�gio ----------
		if(SystemTray.isSupported() == true) {
			
			setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}
		SystemTray bandeja = SystemTray.getSystemTray();
		TrayIcon iconeBandeja = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/vrrecifeframework/img/cofre.png")));


		iconeBandeja.setImageAutoSize(true);
		//iconeBandeja.displayMessage(getWarningString(), "O Integrador est� em execu��o!!", TrayIcon.MessageType.WARNING);
		PopupMenu menuFlutuante = new PopupMenu();
		
		MenuItem mostrar = new MenuItem("Mostrar");
		mostrar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				
				
			}
		});
		MenuItem sair = new MenuItem("Sair");
		sair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		menuFlutuante.add(mostrar);
		menuFlutuante.add(sair);
		iconeBandeja.setPopupMenu(menuFlutuante);
		try {
			bandeja.add(iconeBandeja);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//--------- fim do c�digo da bandeja do rel�gio -------
		
		
		// evento ao clicar no bot�o Iniciar
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIniciar.setEnabled(false);
				btnParar.setEnabled(true);
				btnSair.setEnabled(false);
				lbStatus.setText("Servi�o Iniciado!");

				atividade = true;
				controle(minutos);
				System.out.println("Task scheduled.");
				
			}
		});

		// evento ao clicar no bot�o Parar
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIniciar.setEnabled(true);
				btnSair.setEnabled(true);
				btnParar.setEnabled(false);
				lbStatus.setText("Servico interrompido!");
				atividade = false;
				lbStatus_1.setText("");

			}
		});
		// minimiza o programa na barra de tarefas
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnIniciar.setEnabled(true);
		btnIniciar.setBounds(10, 82, 89, 23);
		desktopPane.add(btnIniciar);

		btnParar.setEnabled(false);
		btnParar.setBounds(143, 82, 89, 23);
		desktopPane.add(btnParar);

		
		btnSair.setVisible(true);
		btnSair.setBounds(277, 82, 89, 23);
		desktopPane.add(btnSair);

		lbStatus.setBounds(20, 41, 191, 23);
		desktopPane.add(lbStatus);
		lbStatus_1.setBounds(220, 41, 166, 23);

		desktopPane.add(lbStatus_1);
	}

	// metodo que controla o timer
	public void controle(int minutos) {
		timer = new Timer();
		timer2 = new Timer();
		timer.schedule(new controleTask(), 0, minutos * (1000 * 60));
		timer2.schedule(new controleTask(), 0,  (1000 * 60));
	}

	// Classe de controle de a��es do timer
	class controleTask extends TimerTask {
		public void run() {
			if (atividade == true) {
				lbStatus_1.setText("Executando!!!!!");
				
				 	envia.geraNotaSaida();
					envia.geraNotaEntrada();
					envia.geraCupom();
					
					Arquivo arquivo = new Arquivo();
					Autorizacao aut = new Autorizacao();
					// cria aqruivo de log
				
														
					List<String> arquivos = new ArrayList<>();
					arquivos = arquivo.listarArquivosXML(Config.diretorio);
					
					System.out.println("Listando arquivos...");
					for(int i=0;i < arquivos.size();i++) {
						System.out.println("Arquivo: "+arquivos.get(i));
						// envia xml e recebe o retorno
						int ret = aut.enviaXml(Config.token,Config.x_integration_key,arquivos.get(i));
						
						// aguarda 3 segundos deois que envia o xml
						try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						if(ret == 1) {
							System.out.println(aut.getIdEnvio());

							String codigo = aut.confirmaProcessamento(Config.token,Config.x_integration_key, aut.getIdEnvio());
							if(codigo.equals("SA2")) {
								System.out.println("Codigo :"+codigo+" Processado!");
							}else {
								arquivo.escreverLog("Arquivo: "+arquivos.get(i)+" - "+Config.msgLog);
								System.out.println(Config.msgLog);
								Config.msgLog = "";
							}
								File f = new File(arquivos.get(i));
								if(f.delete()) {
									System.out.println("deletado!!!");
								}
//												
						}else if(ret == 2) {
							Config.token = aut.retornaToken(Config.x_integration_key, Config.client_id, Config.client_secret, Config.audience);
						}else if(ret == 0){
							System.out.println("Erro na execu��o da aplica��o!");
							
						}else{
//							System.out.println("Erro na execu��o da aplica��o!");
							arquivo.escreverLog("Arquivo: "+arquivos.get(i)+" - "+Config.msgLog);
						}
					}
			
				System.out.println("Ciclo finalizado!");
				lbStatus_1.setText("");
			} else

//			timer.cancel(); // Terminate the timer thread
			lbStatus_1.setText("");
			
		}
		
	}
}
