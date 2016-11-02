package cn.edu.dule.beans.web;

import java.util.List;

import cn.edu.dule.beans.QueryResult;

public class PageView<T> {
	public static final int PAGE_SIZE = 8;
	private long startIndex = 0;
	private long endIndex = 0;
	private long totalRecord;
	private int totalPage;
	private int currentPage;
	private List<T> datas;
	private QueryResult<T> result;
	private int startPage;
	private int endPage;
	
	public PageView(int currentPage) {
		this.currentPage = currentPage;
		startIndex = (currentPage - 1) * PAGE_SIZE;
		endIndex = startIndex + PAGE_SIZE;
	}

	public QueryResult<T> getResult() {
		return result;
	}

	public void setResult(QueryResult<T> result) {
		this.result = result;
		datas = result.getResultList();
		totalRecord = result.getTotalRecord();
		totalPage = (int) (totalRecord%PAGE_SIZE == 0? totalRecord/PAGE_SIZE : totalRecord/PAGE_SIZE+1);
		startPage = (currentPage - PAGE_SIZE/2)>1?currentPage - PAGE_SIZE/2:1;
		endPage = (currentPage + PAGE_SIZE/2)>totalPage?totalPage:currentPage + PAGE_SIZE/2;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public long getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	public long getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public static int getPageSize() {
		return PAGE_SIZE;
	}
	
}
