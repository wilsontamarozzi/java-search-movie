import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class teste {

	public teste() {
		String diretorioRootPath = "D:/BACKUP/torrent/";
		
		List<String> EXTENSOES_FILMES = Arrays.asList("flv", "webm", "mpeg", "vob", "mkv", "mp4", "avi", "mov", "wmv");
		
		String listaExtension = EXTENSOES_FILMES.toString().replace("[", "").replace("]", "").replace(" ", "");
		
		
		/*listaDadosDiretorio(diretorioRootPath, listaExtension);*/
		this.teste4(diretorioRootPath, listaExtension);
	}
	
	public static void main(String args[]) {
		new teste();			
	}
	
	public void teste2(String diretorioRootPath) {
		File dir = new File(diretorioRootPath);
		String[] everythingInThisDir = dir.list();
		for (String name : everythingInThisDir) {
		    System.out.println(name);
		}
	}
	
	public void teste3(String diretorioRootPath) {
		File dir = new File(diretorioRootPath);
		System.out.println(dir.listFiles().length);
		File[] selectedFiles = dir.listFiles(new FileFilter() {
		    @Override
		    public boolean accept(File pathname) {
		        if ("java-io".equals(pathname.getName()))
		            return true;
		        return false;
		    }
		});
		
		for (File selectedFile : selectedFiles) {
		    System.out.println(selectedFile.getAbsolutePath());
		}
	}
	
	public void teste4(String diretorioRootPath, String listaExtension) {
		try {
			PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.{" + listaExtension + "}");
			
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
					
					if (!attrs.isDirectory() && matcher.matches(file.getFileName())) {
						System.out.println(file.toString());
					}
					
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void listaDadosDiretorio(String diretorioRootPath, String listaExtension) {
		try {
			// extensões aceitas
			PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.{" + listaExtension + "}");
			// diretório de busca
			Path diretorio = Paths.get(diretorioRootPath);
			// permite links simbólicos
			EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
			// navega no diretório
			Path teste = Files.walkFileTree(diretorio, options, 1, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// verifica se não é um diretório e se tem uma das extensões
					// esperadas
										
					if (!attrs.isDirectory() && matcher.matches(file.getFileName())) {
						System.out.println(file.toString());
					}
					
					return super.visitFile(file, attrs);
				}
			});
			
			System.out.println(teste);
		} catch( IOException e ) {
			e.printStackTrace();
		}
		
	}
}
