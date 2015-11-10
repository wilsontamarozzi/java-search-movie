package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuracao {

	private static final String APPDATA_PATH = System.getenv("APPDATA");

	private static final String APP_FOLDER = "MovieList";
	
	private static final String THUMBNAIL_FOLDER = "thumbnail";
	
	private static final String COMPLETE_FOLDER_APPDATA_PATH = APPDATA_PATH + "/" + APP_FOLDER + "/";
	
	private static final String ARQUIVO_PROPERTIES = "config.properties";
	
	private static final String ARQUIVO_PROPERTIES_PATH = COMPLETE_FOLDER_APPDATA_PATH + ARQUIVO_PROPERTIES; 
	
	private static final String ARQUIVO_FILELIST_XML = "filelist.xml";
	
	private static final String ARQUIVO_FILELIST_XML_PATH = COMPLETE_FOLDER_APPDATA_PATH + ARQUIVO_FILELIST_XML;
	
	private static final String THUMBNAIL_FOLDER_PATH = COMPLETE_FOLDER_APPDATA_PATH + THUMBNAIL_FOLDER + "/";
	
	private static final List<String> EXTENSOES_FILMES = Arrays.asList("flv", "webm", "mpeg", "mpg", "vob", "mkv", "mp4", "avi", "mov", "wmv");
	
	private String diretorioRootPath;
	
	private List<String> extensoesAceitas = new ArrayList<>(0);
	
	public Configuracao() {
		
	}
	
	public Configuracao(String diretorioRootPath) {
		this.diretorioRootPath = diretorioRootPath;
	}
	
	public Configuracao(String diretorioRootPath, List<String> extensoesAceitas) {
		super();
		this.diretorioRootPath = diretorioRootPath;
		this.extensoesAceitas = extensoesAceitas;
	}

	public String getDiretorioRootPath() {
		return diretorioRootPath;
	}

	public void setDiretorioRootPath(String diretorioRootPath) {
		this.diretorioRootPath = diretorioRootPath;
	}

	public List<String> getExtensoesAceitas() {
		return extensoesAceitas;
	}

	public void setExtensoesAceitas(List<String> extensoesAceitas) {
		this.extensoesAceitas = extensoesAceitas;
	}

	public static String getArquivoPropertiesPath() {
		return ARQUIVO_PROPERTIES_PATH;
	}

	public static List<String> getExtensoesFilmes() {
		return EXTENSOES_FILMES;
	}

	public static String getArquivoFilelistXmlPath() {
		return ARQUIVO_FILELIST_XML_PATH;
	}

	public String getExtensoesAceitasToString() {
		return this.extensoesAceitas.toString().replace("[", "").replace("]", "").replace(" ", "");
	}

	public static String getThumbnailFolderPath() {
		return THUMBNAIL_FOLDER_PATH;
	}
}