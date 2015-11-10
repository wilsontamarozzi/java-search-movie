package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import action.AbstractAction;
import controller.arquivo.ArquivoPopupMenuController;
import controller.configuracao.ConfiguracaoController;
import model.Configuracao;
import model.FiltrosPesquisa;
import model.Video;
import ui.PrincipalFrame;
import ui.dialog.ProgressBarDialog;
import util.ArquivoUtil;
import util.ConvertFileSizeUtil;
import util.DecodeAndCaptureFramesUtil;
import util.ManipuladorXMLUtil;
import util.VideoComparatorUtil;

public class PrincipalController extends AbstractController {

	private PrincipalFrame principalFrame;
	
	private ConfiguracaoController configuracaoController;
	
	private ArquivoPopupMenuController arquivoPopupMenuController;
	
	private List<Video> videos = new ArrayList<>(0);
	
	private Thread t;
		
	public PrincipalController() {
		try {
			this.configuracaoController = new ConfiguracaoController();
			this.carregaConfiguracao();
			this.arquivoPopupMenuController = new ArquivoPopupMenuController();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			this.principalFrame = new PrincipalFrame();
			
			this.registraListeners();
			
			this.criaTabConfiguracao();
			
			this.show();
			
			this.lerDadosXML(videos);
			
			this.refreshJlist(videos);
		}
	}
	
	private void registraListeners() {	
		this.principalFrame.getBtnListar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar(carregaConfiguracao());
			}
		});
		
		this.principalFrame.getTxtTextoPesquisa().getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) { buscaArquivo(getFiltrosPesquisa()); }
			
			public void insertUpdate(DocumentEvent e) { buscaArquivo(getFiltrosPesquisa()); }
			
			public void changedUpdate(DocumentEvent e) {}
		});
		
		this.principalFrame.getJlVideoLista().addMouseListener(new MouseAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						abreArquivo(getJlVideoLista().getSelectedValue().getPath());
					}
				}
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					JList<Video> list = (JList) e.getSource();
					list.setSelectedIndex(list.locationToIndex(e.getPoint()));
					principalFrame.showPopupMenuArquivo(arquivoPopupMenuController.getPopupMenu(), e.getPoint());
				}
			}
		});		
		
		this.principalFrame.getJlVideoLista().addListSelectionListener( new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			    refreshStatusBar(getJlVideoLista().getSelectedValuesList());
			}
		});
		
		this.principalFrame.getCboOrdenarPor().addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				buscaArquivo(getFiltrosPesquisa());
			}
		});
		
		registerAction(this.arquivoPopupMenuController.getPopupMenu().getMnuArquivoAbrir(), new AbstractAction() {
			protected void action() {
				abreArquivo(getJlVideoLista().getSelectedValue().getPath());
			}
		});
		
		registerAction(this.arquivoPopupMenuController.getPopupMenu().getMnuArquivoPropriedade(), new AbstractAction() {
			protected void action() {
				arquivoPopupMenuController.showPropriedade(getJlVideoLista().getSelectedValue());				
			}
		});
		
		registerAction(this.arquivoPopupMenuController.getPopupMenu().getMnuArquivoAbrirLocal(), new AbstractAction() {
			protected void action() {
				arquivoPopupMenuController.abrirLocalArquivo(getJlVideoLista().getSelectedValue());				
			}
		});
		
		registerAction(this.arquivoPopupMenuController.getPopupMenu().getMnuArquivoCopiarNome(), new AbstractAction() {
			protected void action() {
				arquivoPopupMenuController.copiarNomeArquivo(getJlVideoLista().getSelectedValue());				
			}
		});
		
		registerAction(this.arquivoPopupMenuController.getPopupMenu().getMnuArquivoRenomear(), new AbstractAction() {
			protected void action() {
				arquivoPopupMenuController.renomearArquivo(getJlVideoLista().getSelectedValue());
				refreshJlist(videos);
			}
		});
	}
	
	public void criaTabConfiguracao() {
		this.principalFrame.criaTabConfiguracao(this.configuracaoController.getPnlConfiguracao());
	}
	
	public void show() {
		this.principalFrame.setVisible(true);
	}
	
	private Configuracao carregaConfiguracao() {
		return this.configuracaoController.getConfiguracao();
	}
	
	private void listar(Configuracao configuracao) {
		t = new Thread(new Runnable() {
			public void run() {
				videos = ArquivoUtil.listaArquivosDiretorio(configuracao.getDiretorioRootPath(), configuracao.getExtensoesAceitas());
				
				geraThumbnail(videos);
				
				salvarDadosXML(videos);
				
				ordenaArquivo(videos, getFiltrosPesquisa().getOrderBy());
				
				refreshJlist(videos);
		}});
		
		t.start();
	}
	
	private void abreArquivo(String arquivoPath) {
		ArquivoUtil.abreArquivo(arquivoPath);
	}	
	
	public boolean emProgresso = true;
	
	private void geraThumbnail(List<Video> videos) {
		try {
			ProgressBarDialog frame = new ProgressBarDialog();
			frame.getProgressBar().setMaximum(videos.size());
			frame.setVisible(true);
			
			frame.getBtnPausar().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(emProgresso) {
						emProgresso = false;
						frame.getBtnPausar().setText("Iniciar");
					} else {
						emProgresso = true;
						frame.getBtnPausar().setText("Pausar");
					}
				}
			});
			
			frame.getBtnCancelar().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					t.interrupt();
					frame.dispose();
				}
			});
			
			int i = 0;
			while (true) {
				if(emProgresso) {
					if (frame.getProgressBar().getValue() < frame.getProgressBar().getMaximum()) {
						try {
							String oldPath = videos.get(i).getPath();
							String thumbnailPath = Configuracao.getThumbnailFolderPath() + System.nanoTime() + ".jpg";
	
							videos.get(i).setThumbnailPath(thumbnailPath);
	
							frame.getLblArquivos().setText(videos.get(i).getNome());
	
							new DecodeAndCaptureFramesUtil(oldPath, thumbnailPath);
						} catch (Exception e) {
							System.out.println("Houve um erro ao gerar a thumbnail\n" + e);
						}
	
						i++;
						frame.getProgressBar().setValue(i);
					} else {
						frame.dispose();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buscaArquivo(FiltrosPesquisa filtroPesquisa) {
		
		String textoBusca = filtroPesquisa.getTextoBusca();
		String orderBy = filtroPesquisa.getOrderBy();
		
		List<Video> cloneList = new ArrayList<>(0);
				
		for(Video v : this.videos) {
			if(v.getNome().toLowerCase().contains(textoBusca.toLowerCase())) {
				cloneList.add(v);
			}
		}
		
		this.ordenaArquivo(cloneList, orderBy);
		
		this.refreshJlist(cloneList);
	}
	
	private void ordenaArquivo(List<Video> arquivos, String orderby) {
		
		int typeOrder = 0;
		
		switch (orderby.toLowerCase()) {
		case "nome":
			typeOrder = VideoComparatorUtil.ORDERBY_NOME;
			break;
		case "tamanho":
			typeOrder = VideoComparatorUtil.ORDERBY_TAMANHO;
			break;
		case "data modificação":
			typeOrder = VideoComparatorUtil.ODERBY_DATA_ULTIMA_MODIFICACAO;
			break;
		}
		
		VideoComparatorUtil comparatorUtil = new VideoComparatorUtil(typeOrder);
		
		Collections.sort(arquivos, comparatorUtil);
	}
	
	private FiltrosPesquisa getFiltrosPesquisa() {
		return this.principalFrame.getFiltros();
	}
	
	private JList<Video> getJlVideoLista() {
		return this.principalFrame.getJlVideoLista();
	}
	
	private void refreshJlist(List<Video> videos) {
		this.principalFrame.setVideos(videos);
	}
	
	private void refreshStatusBar(List<Video> arquivos) {
		
		double size = 0;
		
		for(Video obj : arquivos) {
			size += obj.getTamanho();
		}
		
		this.principalFrame.refreshStatusBar(String.valueOf(arquivos.size()), ConvertFileSizeUtil.convert((long) size));
	}
	
	private void salvarDadosXML(List<Video> arquivos) {
		ManipuladorXMLUtil.gravaDadosXML(arquivos);
	}
	
	private void lerDadosXML(List<Video> arquivos) {
		ManipuladorXMLUtil.leDadosXML(arquivos);
	}
}
