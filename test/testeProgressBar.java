import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class testeProgressBar {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		new testeProgressBar();
	}
	
	private JLabel lblProgressBar;
	
	private JButton btnCancel;
	
	private JButton btnPauseResume;
	
	private JProgressBar progressBar;
	
	private boolean inProgress = true;
	
	private Thread t;
	
	public testeProgressBar() {
		/*this.setTitle("ProgressBar");*/
		
		JOptionPane.showMessageDialog(null, this.inicializaComponentes());
				
		/*this.setSize(300, 100);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);*/
		
		/*this.timer();*/
	}
	
	private JPanel inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		this.lblProgressBar = new JLabel("Lendo arquivos");
		panel.add(lblProgressBar, BorderLayout.NORTH);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
		panel.add(progressBar, BorderLayout.CENTER);
		
		this.btnCancel = new JButton("Cancelar");
		this.btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.interrupt();
			}
		});
		panel.add(this.btnCancel, BorderLayout.WEST);
		
		this.btnPauseResume = new JButton("Pausar");
		this.btnPauseResume.setActionCommand("pausar");
		this.btnPauseResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("pausar")) {
					btnPauseResume.setText("Iniciar");
					btnPauseResume.setActionCommand("iniciar");
					
					inProgress = false;
				} else {
					btnPauseResume.setText("Pausar");
					btnPauseResume.setActionCommand("pausar");
					
					inProgress = true;
				}
			}
		});
		panel.add(this.btnPauseResume, BorderLayout.SOUTH);
		
		this.timer2();
		
		return panel;
	}
	
	private void timer() {
		try {
			int i = 0;
			while(true) {				
				if(this.inProgress) {
					i++;
					if(this.progressBar.getValue() < 100) {
						this.lblProgressBar.setText("Lendo arquivos: " + i);
						this.progressBar.setValue(i);
						Thread.sleep(20);
					} else {
						break;
					}
				}
			}
		} catch(InterruptedException e) {
			/*e.printStackTrace();*/
		}
	}
	
	private void timer2() {
		t = new Thread(new Runnable() {
			public void run() {
				timer();
			}
		});
		t.start();
	}
}
