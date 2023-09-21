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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import servico.EnviaXML;
import vrrecifeframework.classes.VrProperties;


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
	boolean atividade = false;
	// variavel properties
	VrProperties vr = new VrProperties();
	int minutos;
	private final JLabel lbStatus_1 = new JLabel("");

	/**
	 * Create the frame.
	 */
	public Principal() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/vrrecifeframework/img/cofre.png")));
		minutos = 15;
		setResizable(false);
		setAutoRequestFocus(false);
		setTitle("Sieg - Cofre");
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
		timer.schedule(new controleTask(), 0, minutos * 1000);
	}

	// Classe de controle de ações do timer
	class controleTask extends TimerTask {
		public void run() {
			if (atividade == true) {
				lbStatus_1.setText("Executando!!!!!");
				 	envia.enviaNotaSaida();
					envia.enviaNotaEntrada();
					envia.enviaCupom();
				System.out.println("Time's up!");
				lbStatus_1.setText("");
			} else

			timer.cancel(); // Terminate the timer thread
			lbStatus_1.setText("");
		}
		
	}
}
