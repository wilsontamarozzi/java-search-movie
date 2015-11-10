package ui.dialog;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

public class ProgressBarDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int largura = 400;
	
	private int altura = 120;
		
	private JPanel pnlCenter;
	
	private JLabel lblArquivos;
	
	private JButton btnCancelar;
	
	private JButton btnPausar;
	
	private JProgressBar progressBar;
		
	public ProgressBarDialog() {
		this.UI();
	}
	
	private void UI() {
		this.setTitle("Em progresso");
		
		this.inicializaComponentes();
		
		this.setSize(this.largura, this.altura);
		this.setLocationRelativeTo(null);		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void inicializaComponentes() {
		this.pnlCenter = new JPanel(new MigLayout());
		this.add(this.pnlCenter, BorderLayout.CENTER);
		
		this.lblArquivos = new JLabel("Listando ...");
		this.pnlCenter.add(this.lblArquivos, "w 100%, wrap");
		
		this.progressBar = new JProgressBar();
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
		this.pnlCenter.add(this.progressBar, "w 100%, wrap");
		
		this.btnCancelar = new JButton("Cancelar");
		this.pnlCenter.add(this.btnCancelar, "split");
		
		this.btnPausar = new JButton("Pausar");
		this.pnlCenter.add(this.btnPausar, "wrap");
	}

	public JLabel getLblArquivos() {
		return lblArquivos;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnPausar() {
		return btnPausar;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}	
}
