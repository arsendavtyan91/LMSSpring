package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

abstract public class BaseDAO<T> {
	
	@Autowired
	JdbcTemplate template;
	
	private int pageNo = -1;
	
	private int pageSize = 10;

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String addLimit(String query) {
		String result = "";
		if (pageNo > -1) {
			int start = (pageNo - 1) * pageSize;
			if (start > 0) {
				result = " LIMIT " + start + ", " + pageSize;
			} else {
				result = " LIMIT 0, " + pageSize;
			}
		}
		return query + result;
	}
}
