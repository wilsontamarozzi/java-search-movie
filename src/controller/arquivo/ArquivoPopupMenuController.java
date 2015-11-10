package controller.arquivo;

import action.AbstractAction;
import controller.AbstractController;
import model.Video;
import ui.popupmenu.JPopupMenuArquivo;
import util.ArquivoUtil;
import util.TextTransferUtil;

public class ArquivoPopupMenuController extends AbstractController {

	private JPopupMenuArquivo frame;
	
	public ArquivoPopupMenuController() {
		this.frame = new JPopupMenuArquivo();
		
		this.registraListeners();
	}
	
	private void registraListeners() {
		registerAction(this.frame.getMnuArquivoAbrir(), new AbstractAction() {
			protected void action() {
				//Deixando a função em aberto para possiveis modificações
			}
		});
	}
	
	public void showPropriedade(Video arquivo) {
		ArquivoUtil.exibePropriedadeArquivo(arquivo);
	}
	
	public void abrirLocalArquivo(Video arquivo) {		
		ArquivoUtil.abreArquivo(arquivo.getDiretorioPath());
	}
	
	public void renomearArquivo(Video arquivo) {
		ArquivoUtil.renomarArquivo(arquivo);
	}
		
	public void copiarNomeArquivo(Video arquivo) {
		TextTransferUtil textTransferUtil = new TextTransferUtil();
		textTransferUtil.setClipboardContents(arquivo.getNome());
	}

	public JPopupMenuArquivo getPopupMenu() {
		return frame;
	}
}
