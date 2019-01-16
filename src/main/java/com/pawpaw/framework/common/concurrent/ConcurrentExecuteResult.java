package com.pawpaw.framework.common.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * identify the result after finish concurrent execution
 * 
 * @author liujixin
 *
 * @param <V>
 */
public class ConcurrentExecuteResult<V> {

	private List<V> completeResults;
	private List<Exception> exceptions;

	public ConcurrentExecuteResult() {
		this(new ArrayList<V>(0), new ArrayList<Exception>(0));
	}

	public ConcurrentExecuteResult(List<V> completeResults, List<Exception> exceptions) {
		if (completeResults == null) {
			completeResults = new ArrayList<V>(0);
		}
		this.completeResults = completeResults;
		if (exceptions == null) {
			exceptions = new ArrayList<Exception>(0);
		}
		this.exceptions = exceptions;
	}

	public void addCompleteResult(V v) {
		this.completeResults.add(v);
	}

	public void addException(Exception e) {
		this.exceptions.add(e);
	}

	public void merge(com.pawpaw.common.concurrent.ConcurrentExecuteResult<V> target) {
		if (target.getCompleteResults() != null) {
			this.completeResults.addAll(target.getCompleteResults());
		}
		if (target.getExceptions() != null) {
			this.exceptions.addAll(target.getExceptions());
		}
	}

	@Override
	public String toString() {
		return "succ=" + this.completeResults.toString() + " ; exception=" + this.exceptions.toString();
	}

	

	public List<V> getCompleteResults() {
		return completeResults;
	}

	public List<Exception> getExceptions() {
		return exceptions;
	}

}
