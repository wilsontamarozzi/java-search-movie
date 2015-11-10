package ui.panel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Configuracao;
import net.miginfocom.swing.MigLayout;

public class JPanelConfiguracao extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtDiretorio;
	
	private JButton btnProcurar;
	
	private JButton btnLimparTudo;
	
	private JButton btnSalvar;
	
	private JButton btnSelecionaExtensao;
	
	private JButton btnLimpaExtensao;
	
	private JFileChooser fileChooser;
	
	private List<JCheckBox> extensoesAceitas = new ArrayList<>(0);
	
	private JPanel pnlExtensoesAceitas;
	
	private Configuracao configuracao;

	public JPanelConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
		this.UI();
	}
	
	private void UI() {
		this.setName("Configuração");
		this.setLayout(new MigLayout());
		
		this.inicializaComponentes();
	}
	
	private void inicializaComponentes() {
		this.criaFileChooser();
		this.panelConfiguracaoGeral();
		this.panelExtensoesAceitas();
		this.panelBotoes();
	}
	
	private void panelConfiguracaoGeral() {
		JPanel pnlGeral = new JPanel(new MigLayout());
		pnlGeral.setBorder(BorderFactory.createTitledBorder("Geral"));
		this.add(pnlGeral, "width 100%, wrap");
		
		pnlGeral.add(new JLabel("Diretório"), "split");
		
		this.txtDiretorio = new JTextField();
		pnlGeral.add(this.txtDiretorio, "width 50%");
		
		this.btnProcurar = new JButton("Procurar");
		pnlGeral.add(this.btnProcurar);		
	}
	
	private void panelExtensoesAceitas() {
		this.pnlExtensoesAceitas = new JPanel(new MigLayout());
		this.pnlExtensoesAceitas.setBorder(BorderFactory.createTitledBorder("Extensões Aceitas"));
		this.add(this.pnlExtensoesAceitas, "width 100%, wrap");
		
		this.btnSelecionaExtensao = new JButton("Seleciona tudo");
		this.pnlExtensoesAceitas.add(this.btnSelecionaExtensao, "split");
		
		this.btnLimpaExtensao = new JButton("Limpa tudo");
		this.pnlExtensoesAceitas.add(this.btnLimpaExtensao, "wrap");
		
		for(String extensao : Configuracao.getExtensoesFilmes()) {			
			JCheckBox chkExtensao = new JCheckBox(extensao);
			chkExtensao.setActionCommand(extensao);
			
			if(this.configuracao.getExtensoesAceitas().contains(extensao)) {
				chkExtensao.setSelected(true);
			}
			
			this.extensoesAceitas.add(chkExtensao);
			this.pnlExtensoesAceitas.add(chkExtensao, "split, width 60!");
		}
	}
	
	private void panelBotoes() {
		JPanel pnlBotoes = new JPanel(new MigLayout());
		pnlBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));
		this.add(pnlBotoes, "width 100%");
		
		this.btnSalvar = new JButton("Salvar");
		pnlBotoes.add(this.btnSalvar);
		
		this.btnLimparTudo = new JButton("Limpar");
		pnlBotoes.add(this.btnLimparTudo);
	}
	
	private void criaFileChooser() {
		this.fileChooser = new JFileChooser();
		/*this.fileChooser.setCurrentDirectory(new java.io.File("."));*/
		this.fileChooser.setDialogTitle("Selecione o Diretório");
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	public void showFileChooser() {
		if (this.fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.txtDiretorio.setText(this.fileChooser.getSelectedFile().toString());
		}
	}
	
	private Configuracao loadConfiguracao() {
		
		String diretorioRootPath = "";
		if(!this.txtDiretorio.getText().isEmpty()) {
			diretorioRootPath = this.txtDiretorio.getText().replace("\\", "/");
		}
		
		List<String> extensoesAceitas = new ArrayList<>();
		for(JCheckBox check : this.extensoesAceitas) {
			if(check.isSelected()) {
				extensoesAceitas.add(check.getActionCommand());
			}
		}
		
		return new Configuracao(diretorioRootPath, extensoesAceitas);
	}
		
	private void populateTextFields(Configuracao c) {
		this.txtDiretorio.setText(c.getDiretorioRootPath());
	}
	
	public void resetForm() {
		this.txtDiretorio.setText("");
		
		for(JCheckBox check : this.extensoesAceitas) {
			check.setSelected(false);
		}
	}
	
	public Configuracao getConfiguracao() {
		return this.loadConfiguracao();
	}
	
	public void setConfiguracao(Configuracao c) {
		if(c != null) {
			this.populateTextFields(c);
		}
	}

	public JButton getBtnProcurar() {
		return btnProcurar;
	}

	public JButton getBtnLimparTudo() {
		return btnLimparTudo;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnSelecionaExtensao() {
		return btnSelecionaExtensao;
	}

	public JButton getBtnLimpaExtensao() {
		return btnLimpaExtensao;
	}

	public JPanel getPnlExtensoesAceitas() {
		return pnlExtensoesAceitas;
	}
}
