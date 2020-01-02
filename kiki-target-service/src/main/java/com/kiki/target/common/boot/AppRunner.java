package com.kiki.target.common.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.kiki.target.module.target.TargetService;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月30日
 */
@Component
public class AppRunner implements ApplicationRunner{
	
	private Logger logger = LoggerFactory.getLogger(AppRunner.class);
	@Autowired
	TargetService targetService;
	/* （非 Javadoc）
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("------------------------AppRunner start update target dealine----------------------");
		int[] record = targetService.expireCheck();
		logger.info("------------------------AppRunner stop and update "+record[0]+" user's record and update "+record[1]+" target record----------------------");
	}
}
