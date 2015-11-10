package controller.configuracao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import model.Configuracao;
import ui.panel.JPanelConfiguracao;
import util.ManipuladorPropertiesUtil;

public class ConfiguracaoController {

	private JPanelConfiguracao pnlConfiguracao;
	
	private Configuracao configuracao;
	
	public ConfiguracaoController() {
		try {
			this.configuracao = new Configuracao();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ConfiguraçãoController");
		} finally {
			this.inicializaConfiguracao();
			
			this.pnlConfiguracao = new JPanelConfiguracao(this.configuracao);
			
			this.pnlConfiguracao.setConfiguracao(this.configuracao);
			
			this.registraListeners();
		}
	}
	
	private void registraListeners() {
		this.pnlConfiguracao.getBtnProcurar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurar();
			}
		});
		
		this.pnlConfiguracao.getBtnSalvar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarConfiguracao();
			}
		});
		
		this.pnlConfiguracao.getBtnLimparTudo().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetForm();
			}
		});
		
		this.pnlConfiguracao.getBtnSelecionaExtensao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaTodasExtensoes(true);
			}
		});
		
		this.pnlConfiguracao.getBtnLimpaExtensao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaTodasExtensoes(false);
			}
		});
	}
	
	private void resetForm() {
		this.pnlConfiguracao.resetForm();
	}
	
	public void inicializaConfiguracao() {
		this.leConfiguracaoProperties(this.configuracao);
	}
	
	private void leConfiguracaoProperties(Configuracao configuracao) {
		try {
			ManipuladorPropertiesUtil propertiesUtil = new ManipuladorPropertiesUtil();
			
			Properties configSistema = propertiesUtil.checkExist(Configuracao.getArquivoPropertiesPath());
			
			if(configSistema != null) {
				configuracao.setDiretorioRootPath(propertiesUtil.getPropriedade(configSistema, "diretorio.path.movie"));
				configuracao.setExtensoesAceitas(Arrays.asList((propertiesUtil.getPropriedade(configSistema, "extensoes.filmes.aceitas")).split(",")));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Houve um erro ao ler as configurações");
			e.printStackTrace();
		}
	}
	
	private void procurar() {
		this.pnlConfiguracao.showFileChooser();
	}
	
	private void salvarConfiguracao() {
		this.configuracao = this.pnlConfiguracao.getConfiguracao();
		
		if(this.configuracao != null) {
			this.gravaConfiguracaoProperties(this.configuracao);
		}
	}
	
	private void gravaConfiguracaoProperties(Configuracao configuracao) {
		try {
			ManipuladorPropertiesUtil propertiesUtil = new ManipuladorPropertiesUtil();
			
			Properties configSistema = propertiesUtil.checkExist(Configuracao.getArquivoPropertiesPath());
			
			if(configSistema != null) {
				propertiesUtil.incluirPropriedade("diretorio.path.movie", configuracao.getDiretorioRootPath(), configSistema);
				propertiesUtil.incluirPropriedade("extensoes.filmes.aceitas", configuracao.getExtensoesAceitasToString(), configSistema);
				
				propertiesUtil.salvar(configSistema, Configuracao.getArquivoPropertiesPath());
				
				JOptionPane.showMessageDialog(null, "Configurações salvar com sucesso.");
			}			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Houve um erro ao salvar as configurações.");
			e.printStackTrace();
		}
	}
	
	private void selecionaTodasExtensoes(boolean opcao) {
		for(Component c : this.pnlConfiguracao.getPnlExtensoesAceitas().getComponents()) {
			if(c instanceof JCheckBox) {
				((JCheckBox) c).setSelected(opcao);
			}
		}
	}

	public Configuracao getConfiguracao() {
		return configuracao;
	}

	public JPanelConfiguracao getPnlConfiguracao() {
		return pnlConfiguracao;
	}	
}
