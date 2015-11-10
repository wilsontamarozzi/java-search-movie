package ui.popupmenu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class JPopupMenuArquivo extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	private JMenuItem mnuArquivoAbrir;
	
	private JMenuItem mnuArquivoAbrirLocal;
	
	private JMenuItem mnuArquivoCopiarNome;
	
	private JMenuItem mnuArquivoRenomear;
	
	private JMenuItem mnuArquivoPropriedade;
	
	public JPopupMenuArquivo() {
		this.UI();
	}
	
	private void UI() {
		this.inicializaComponentes();
	}
	
	private void inicializaComponentes() {
		this.criaPopupMenu();
	}
	
	private void criaPopupMenu() {
		this.mnuArquivoAbrir = new JMenuItem("Abrir");
		this.mnuArquivoAbrir.setActionCommand("mnuArquivoAbrir");
		this.add(this.mnuArquivoAbrir);
		
		this.mnuArquivoAbrirLocal = new JMenuItem("Abrir local");
		this.mnuArquivoAbrirLocal.setActionCommand("mnuArquivoAbrirLocal");
		this.add(this.mnuArquivoAbrirLocal);
		
		this.mnuArquivoCopiarNome = new JMenuItem("Copiar Nome");
		this.mnuArquivoCopiarNome.setActionCommand("mnuArquivoCopiarNome");
		this.add(this.mnuArquivoCopiarNome);
		
		this.mnuArquivoRenomear = new JMenuItem("Renomear");
		this.mnuArquivoRenomear.setActionCommand("mnuArquivRenomear");
		this.add(this.mnuArquivoRenomear);		
	
		this.mnuArquivoPropriedade = new JMenuItem("Propriedade");
		this.mnuArquivoPropriedade.setActionCommand("mnuArquivPropriedade");
		this.add(this.mnuArquivoPropriedade);
	}

	public JMenuItem getMnuArquivoAbrir() {
		return mnuArquivoAbrir;
	}

	public void setMnuArquivoAbrir(JMenuItem mnuArquivoAbrir) {
		this.mnuArquivoAbrir = mnuArquivoAbrir;
	}

	public JMenuItem getMnuArquivoCopiarNome() {
		return mnuArquivoCopiarNome;
	}

	public JMenuItem getMnuArquivoRenomear() {
		return mnuArquivoRenomear;
	}

	public JMenuItem getMnuArquivoPropriedade() {
		return mnuArquivoPropriedade;
	}

	public JMenuItem getMnuArquivoAbrirLocal() {
		return mnuArquivoAbrirLocal;
	}
}
