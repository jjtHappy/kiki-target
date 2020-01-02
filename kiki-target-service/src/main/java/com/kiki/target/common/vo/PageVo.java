/**
 * 
 */
package com.kiki.target.common.vo;



/**
 * @author jiangjintai
 *
 */
public class PageVo implements Vo {
	private int number;
	private int totalPages;
	private long totalElements;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	
}
