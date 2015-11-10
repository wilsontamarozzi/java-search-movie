package util;

import java.util.Comparator;

import model.Video;

public class VideoComparatorUtil implements Comparator<Video> {

	public static final int ORDERBY_NOME = 1;
	
	public static final int ORDERBY_TAMANHO = 2;
	
	public static final int ODERBY_DATA_ULTIMA_MODIFICACAO = 3;
	
	private int orderBy;
	
	public VideoComparatorUtil(int orderBy) {
		this.orderBy = orderBy;
		
	}
	
	@Override
	public int compare(Video o1, Video o2) {
		switch (orderBy) {
		case 1:
			return o1.getNome().toLowerCase().compareTo(o2.getNome().toLowerCase());
		case 2:
			if(o1.getTamanho() > o2.getTamanho())
				return -1;
			
			if(o1.getTamanho() < o2.getTamanho())
				return 1;
			
			return 0;
		case 3:
			if(o1.getDataUltimaModificacao().after(o2.getDataUltimaModificacao()))
				return -1;
			
			if(o1.getDataUltimaModificacao().before(o2.getDataUltimaModificacao()))
				return 1;
			
			return 0;
		default:
			return o1.getNome().toLowerCase().compareTo(o2.getNome().toLowerCase());
		}		
	}	
}
