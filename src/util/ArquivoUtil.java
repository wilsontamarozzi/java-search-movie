package util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Video;

public class ArquivoUtil {
		
	public static List<Video> listaArquivosDiretorio(String diretorioRootPath, List<String> extensoesAceitas) {
		
		List<Video> videos = new ArrayList<Video>(0);
		
		try {
			/*PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.{" + extensoesAceitas + "}");*/
			
			Path diretorio = FileSystems.getDefault().getPath(diretorioRootPath);
			
			Files.walkFileTree(diretorio, new FileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

					File arquivo = new File(file.toString());
					/*if (!attrs.isDirectory() && matcher.matches(file.getFileName())) {*/
					if(!attrs.isDirectory() && extensoesAceitas.contains(getFileExtension(arquivo))) {
						Video video = new Video();
						
						video.setNome(arquivo.getName());
						video.setPath(arquivo.getAbsolutePath());
						video.setTamanho(arquivo.length());
						video.setDataUltimaModificacao(arquivo.lastModified());
						video.setExtensao(getFileExtension(arquivo));
						video.setDiretorioPath(arquivo.getParentFile().toString());

						videos.add(video);
					}					
					
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return videos;
	}
	
	private static String getFileExtension(File file) {

		String filename = file.getName().toLowerCase();
		String[] splited = filename.split("\\.");

		return splited.length >= 2 ? splited[splited.length - 1] : "";
	}
	
	public static void abreArquivo(String arquivoPath) {
		try {
			if (Desktop.isDesktopSupported()) {
			    Desktop.getDesktop().open(new File(arquivoPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void renomarArquivo(Video arquivo) {
		String nomeNovo = JOptionPane.showInputDialog("Insira o novo nome."
				+ "\nNão utilize os caracteres abaixo:"
				+ "\n \\ / ? : * \" > < |", arquivo.getNome());
		
		if(nomeNovo != null) {			
			String oldPath = arquivo.getPath();
			File dirPath = new File(oldPath.substring(0, oldPath.lastIndexOf(File.separator)));
			
			nomeNovo = removeCaracteresInvalidos(nomeNovo);
			
			String newPath = dirPath + File.separator + nomeNovo;
			
			boolean sucesso = new File(oldPath).renameTo(new File(newPath));
			
			if(sucesso) {
				System.out.println("Arquivo renomeado com sucesso.");
				arquivo.setNome(nomeNovo);
				arquivo.setPath(newPath);
			} else {
				System.out.println("Houve um erro ao renomear arquivo.");
			}
		}
	}
	
	private static String removeCaracteresInvalidos(String texto) {
		String caracteresInvalidos[] = {"\\", "/", "?", ":", "*", "\"", ">", "<", "|"};	
		
		for(String c : caracteresInvalidos) {
			texto = texto.replace(c, "");
		}		
		
		return texto;
	}
	
	public static void exibePropriedadeArquivo(Video arquivo) {
		JOptionPane.showMessageDialog(null, 
				"Nome: " + arquivo.getNome() + "\n" +
				"Tamanho: " + ConvertFileSizeUtil.convert((long) arquivo.getTamanho()) + "\n" +
				"Path: " + arquivo.getPath());
	}
	
	public static void limpaDiretorio(String diretorioPath) {
		
	}
}
