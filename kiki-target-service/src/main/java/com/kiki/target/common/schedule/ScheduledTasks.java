package com.kiki.target.common.schedule;

import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kiki.target.module.target.TargetService;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月30日
 */
@Component
public class ScheduledTasks {
	@Autowired
	TargetService targetService;
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	
	@Scheduled(cron = "0 0 0 * * *",zone="GMT+8")//每天的凌晨12点
	public void reportCurrentTime() {
		logger.info("------------------------ScheduledTask start update target dealine----------------------");
		int[] record = targetService.expireCheck();
		logger.info("------------------------ScheduledTask stop and update "+record[0]+" user's record and update "+record[1]+" target record----------------------");
	}
}
