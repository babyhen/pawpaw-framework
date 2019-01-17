package com.pawpaw.framework.common.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * 结果页
 * 
 * @author liujixin
 *
 * @param <T>
 */
public class PageResp<T> {

	protected long totalSize;
	protected int currPageNo;
	protected int pageSize;
	protected List<T> pageData;

	public void addItem(T t) {
		if (this.pageData == null) {
			this.pageData = new LinkedList<>();
		}
		this.pageData.add(t);
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		sb.append("totalSize=").append(this.totalSize);
		sb.append(",currPageNo=").append(this.currPageNo);
		sb.append(",pageSize=").append(this.pageSize);
		sb.append(",pageData=").append(this.pageData);
		sb.append("]");
		return sb.toString();
	}
	
	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public int getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

}
