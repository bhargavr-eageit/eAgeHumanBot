package com.eAge.humanbot.model;

public class ClientQuery {

	private String queryPreText;
	
	private String query;

	public ClientQuery(String queryPreText, String query) {
		super();
		this.queryPreText = queryPreText;
		this.query = query;
	}

	public String getQueryPreText() {
		return queryPreText;
	}

	public void setQueryPreText(String queryPreText) {
		this.queryPreText = queryPreText;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString() {
		return queryPreText + query +".";
	}
}
