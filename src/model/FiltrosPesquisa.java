package model;

public class FiltrosPesquisa {

	private String textoBusca;
	
	private String orderBy;
	
	public FiltrosPesquisa() {
		
	}

	public FiltrosPesquisa(String textoBusca, String orderBy) {
		super();
		this.textoBusca = textoBusca;
		this.orderBy = orderBy;
	}

	public String getTextoBusca() {
		return textoBusca;
	}

	public void setTextoBusca(String textoBusca) {
		this.textoBusca = textoBusca;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}	
}
