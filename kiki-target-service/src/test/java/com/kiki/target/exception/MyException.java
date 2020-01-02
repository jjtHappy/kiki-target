package com.kiki.target.exception;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年4月24日
 */
public class MyException extends Exception{

	/**
	 * Title:
	 * Description:
	 * @author jjtEatJava
	 * @date 2018年4月24日
	 */
	private static final long serialVersionUID = 8449854159169922994L;
	
	
	public static void  foo() throws MyException {
		throw new MyException();
	}

	public static void main(String[] args) throws MyException {
		MyException.foo();
	}
}
