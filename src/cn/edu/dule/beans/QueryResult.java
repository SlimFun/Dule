package cn.edu.dule.beans;

import java.util.ArrayList;
import java.util.List;

public class QueryResult<T> {
	private List<T> resultList;
	private long totalRecord;
	public QueryResult() {
		// TODO Auto-generated constructor stub
		resultList = new ArrayList<T>();
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	
}
