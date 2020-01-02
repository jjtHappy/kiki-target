package com.kiki.target.source;

import org.junit.Assert;
import org.junit.Test;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月1日
 */
public class TestResource {
	@Test
	public  void  tesySuffix() {
		String suffix = "jpg|png";
		String jpg="jpg";
		String png = "png";
		Assert.assertTrue(jpg.matches(suffix));
		Assert.assertTrue(png.matches(suffix));
	}
}
