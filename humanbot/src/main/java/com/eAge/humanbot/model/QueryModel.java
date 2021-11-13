package com.eAge.humanbot.model;

public class QueryModel {

	private String query;
	private int answer;


	public QueryModel(String query, int answer) {
		super();
		this.query = query;
		this.answer = answer;
	}

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
}
