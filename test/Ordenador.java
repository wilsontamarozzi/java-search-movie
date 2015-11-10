import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Ordenador {

	public static void main(String args[]) {
		 new Ordenador();
	}
	
	public Ordenador() {
		teste2();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void teste1() {
		String vetor[] = new String[4];  
		  
        vetor[0] = "Café";  
        vetor[1] = "Arroz";  
        vetor[2] = "Xícara";  
        vetor[3] = "Remedio";   
          
        Arrays.sort(vetor, new Comparator() {  
              
            public int compare(Object o1, Object o2) {  
                String a = (String) o1;  
                String b = (String) o2;  
                return a.compareTo(b);  
            }  
        });  
  
        for(int i = 0;i < vetor.length; i++){  
            System.out.println(vetor[i]);  
        } 
	}
	
	public void teste2() {
		
		List<String> dados = new ArrayList<String>(0);
		
		try {
			File file = new File("C:/Users/Monde/Downloads/teste.txt");
			
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;
			
			while((data = reader.readLine()) != null){
				dados.add(data);				
			}
			
			fileReader.close();
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
						
			for(int i = 0; i < dados.size(); i++) {
				System.out.println(i);
			}
		}
	}
}
