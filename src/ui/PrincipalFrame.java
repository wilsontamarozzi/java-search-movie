package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import model.FiltrosPesquisa;
import model.Video;
import net.miginfocom.swing.MigLayout;
import renderer.JListVideoRenderer;
import ui.panel.JPanelConfiguracao;
import ui.popupmenu.JPopupMenuArquivo;

public class PrincipalFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private int largura = 700;
	
	private int altura = 400;
	
	private JPanel pnlCenter;
			
	private JTabbedPane tabMenuGeral;
	
	private JTextField txtTextoPesquisa;
	
	private JButton btnListar;
	
	private JList<Video> jlVideoLista; 
	
	private JScrollPane spVideoLista;
	
	private JLabel lblItensCount;
	
	private JLabel lblItensSize;
	
	private JComboBox<String> cboOrdenarPor;
	
	public PrincipalFrame() {
		this.UI();
	}
	
	private void UI() {
		this.setTitle("Movie List");
		
		this.inicializaComponentes();
		
		this.pack();
		this.setSize(this.largura, this.altura);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void inicializaComponentes() {
		this.pnlCenter = new JPanel(new MigLayout());
		this.add(this.pnlCenter, BorderLayout.CENTER);
		
		this.criaTabMenuGeral();
		this.criaTabDetalhes();
	}
	
	private void criaTabMenuGeral() {
		this.tabMenuGeral = new JTabbedPane();
		this.pnlCenter.add(this.tabMenuGeral, "push, grow");
	}
	
	private void criaTabDetalhes() {
		JPanel pnlDetalhes = new JPanel(new MigLayout());
		this.tabMenuGeral.add(pnlDetalhes, "Filmes");
		
		pnlDetalhes.add(new JLabel("Pesquisa"), "split");
		
		this.txtTextoPesquisa = new JTextField();
		pnlDetalhes.add(this.txtTextoPesquisa, "w 40%");
		
		String[] orderBy = {"Nome", "Tamanho", "Data modificação"};
		
		this.cboOrdenarPor = new JComboBox<String>(orderBy);
		pnlDetalhes.add(this.cboOrdenarPor);
		
		this.btnListar = new JButton("Atualizar");
		pnlDetalhes.add(this.btnListar, "wrap");
			
		this.jlVideoLista = new JList<>();
		this.jlVideoLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.jlVideoLista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.jlVideoLista.setVisibleRowCount(-1);		
		this.jlVideoLista.setCellRenderer(new JListVideoRenderer());
		
		this.spVideoLista = new JScrollPane(this.jlVideoLista);
		pnlDetalhes.add(this.spVideoLista, "push, grow, wrap");
		pnlDetalhes.add(this.criaStatusBar(), "w 100%");
	}
	
	private JPanel criaStatusBar() {
		JPanel pnlStatusBar = new JPanel(new MigLayout());
		pnlStatusBar.setBorder(BorderFactory.createEtchedBorder());
		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(0, 20));
		
		JPanel pnlItensCount = new JPanel();
		pnlStatusBar.add(pnlItensCount, "w 50");
		
		this.lblItensCount = new JLabel("Itens");
		pnlItensCount.add(this.lblItensCount);
		
		pnlStatusBar.add(separator);
		
		JPanel pnlItensSize = new JPanel();
		pnlStatusBar.add(pnlItensSize, "w 50");
		
		this.lblItensSize = new JLabel("Tamanho");
		pnlItensSize.add(this.lblItensSize);
		
		return pnlStatusBar;
	}
	
	public void criaTabConfiguracao(JPanelConfiguracao p) {
		this.tabMenuGeral.add(p, p.getName());
	}
	
	public void showPopupMenuArquivo(JPopupMenuArquivo pop, Point position) {
		pop.show(this.jlVideoLista, (int) position.getX(), (int) position.getY());
	}
	
	public void refreshStatusBar(String itensCount, String itensSize) {
		this.lblItensCount.setText(itensCount);
		this.lblItensSize.setText(itensSize);
	}
		
	public void setVideos(List<Video> videos) {		
		Video[] itens = new Video[videos.size()];
		itens = videos.toArray(itens);
		
		this.jlVideoLista.setListData(itens);
	}
	
	private FiltrosPesquisa loadFiltros() {
		String textoBusca = "";
		if(!this.txtTextoPesquisa.getText().isEmpty()) {
			textoBusca = this.txtTextoPesquisa.getText();
		}
		
		String orderBy = "";
		if(this.cboOrdenarPor.getSelectedIndex() > -1) {
			orderBy = this.cboOrdenarPor.getSelectedItem().toString();
		}
		
		return new FiltrosPesquisa(textoBusca, orderBy);
	}
	
	public FiltrosPesquisa getFiltros() {
		return this.loadFiltros();
	}

	public JList<Video> getJlVideoLista() {
		return jlVideoLista;
	}

	public JButton getBtnListar() {
		return btnListar;
	}

	public JTextField getTxtTextoPesquisa() {
		return txtTextoPesquisa;
	}

	public JComboBox<String> getCboOrdenarPor() {
		return cboOrdenarPor;
	}
}
