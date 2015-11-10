package model;

import java.util.Date;

import javax.swing.ImageIcon;

public class Video {

	private int videoId;
	
	private String nome;
	
	private double tamanho;
	
	private Date dataUltimaModificacao = new Date();
	
	private String path;
	
	private String thumbnailPath;
	
	private ImageIcon thumbnail;
	
	private String extensao;
	
	private String diretorioPath;
	
	public Video() {
		
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}

	public Date getDataUltimaModificacao() {
		return dataUltimaModificacao;
	}

	public void setDataUltimaModificacao(Date dataUltimaModificacao) {
		this.dataUltimaModificacao = dataUltimaModificacao;
	}
	
	public void setDataUltimaModificacao(long dataUltimaModificacao) {
		this.dataUltimaModificacao = new Date(dataUltimaModificacao);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public ImageIcon getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ImageIcon thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getDiretorioPath() {
		return diretorioPath;
	}

	public void setDiretorioPath(String diretorioPath) {
		this.diretorioPath = diretorioPath;
	}
}
