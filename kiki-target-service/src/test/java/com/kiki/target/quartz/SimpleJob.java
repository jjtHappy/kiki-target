package com.kiki.target.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月30日
 */
public class SimpleJob implements Job{

	/* （非 Javadoc）
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-----------触发器触发-----------");	
	}

}
