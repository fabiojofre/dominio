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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import servico.Arquivo;
import servico.Autorizacao;
import servico.Config;
import servico.EnviaXML;
import servico.ServicoConfig;


public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;
	
	

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
					
					if(Config.x_integration_key == null || Config.x_integration_key.equals("")) {
						int val = JOptionPane.showConfirmDialog(null, "Deseja gerar novo token?");
						if(val == 0) { 
							Autorizacao aut = new Autorizacao();
							System.out.println("Gerando novo token...");
							if(Config.token.length() > 20) {
							Config.token = aut.retornaToken(Config.x_integration_key, Config.client_id, Config.client_secret, Config.audience);
							}else {
								Config.token = aut.retornaToken(Config.chave_temporaria, Config.client_id, Config.client_secret, Config.audience);
							}
							JOptionPane.showMessageDialog(null, "Token gerado com sucesso!");
							System.out.println("Token gerado: "+Config.token);
						}
						int val2 = JOptionPane.showConfirmDialog(null, "Deseja gerar nova chave?");
						if (val2 == 0) {
							Autorizacao aut = new Autorizacao();
							System.out.println("Gerando nova chave...");
							if(Config.token.length() < 20) {
							Config.x_integration_key = aut.retornaChaveIntegracao(Config.token, Config.chave_temporaria);
							}else {
								Config.x_integration_key = aut.retornaChaveIntegracao(Config.token, Config.chave_temporaria);
							}
							System.out.println("Chave gerada: "+Config.x_integration_key);
						}
					}
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
		
		//------ O Icone na bandeja do relógio ----------
		if(SystemTray.isSupported() == true) {
			
			setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}
		SystemTray bandeja = SystemTray.getSystemTray();
		TrayIcon iconeBandeja = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/vrrecifeframework/img/cofre.png")));


		iconeBandeja.setImageAutoSize(true);
		//iconeBandeja.displayMessage(getWarningString(), "O Integrador está em execução!!", TrayIcon.MessageType.WARNING);
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
		
		//--------- fim do código da bandeja do relógio -------
		
		
		// evento ao clicar no botão Iniciar
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIniciar.setEnabled(false);
				btnParar.setEnabled(true);
				btnSair.setEnabled(false);
				lbStatus.setText("Serviço Iniciado!");

				atividade = true;
				controle(minutos);
				System.out.println("Task scheduled.");
				
			}
		});

		// evento ao clicar no botão Parar
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
	
	
	// Classe de controle de ações do timer
	class controleTask extends TimerTask {
		public void run() {
			if (atividade == true) {
			lbStatus_1.setText("Processando!!!");
			EnviaXML envia = new EnviaXML();
			System.out.println("\n");
			System.out.println("Enviando bloco de NFe-Saida...\n");
			
			envia.geraNotaSaida();
			
			System.out.println("\n");
			System.out.println("Enviando bloco de NFe-Entrada...\n");
			
			envia.geraNotaEntrada();
			
			System.out.println("\n");
			System.out.println("Enviando bloco de NFc-e (Cupons fiscais) ...\n");
			
			envia.geraCupom();
			
			lbStatus_1.setText("");
			System.out.println("Processo finalizado!!");
			}
	}
	
}
}
