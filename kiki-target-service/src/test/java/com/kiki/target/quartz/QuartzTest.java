package com.kiki.target.quartz;

import java.util.Date;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月30日
 */
public class QuartzTest {

	public  static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		// define the job and tie it to our HelloJob class
		  JobDetail job = JobBuilder.newJob(SimpleJob.class).build();
		  // 触发器  
          TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
          // 触发器名,触发器组  
          triggerBuilder.startNow();
          // 触发器时间设定  
          triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"));
          // 创建Trigger对象
          CronTrigger trigger = (CronTrigger) triggerBuilder.build();

          // 调度容器设置JobDetail和Trigger
          scheduler.scheduleJob(job, trigger);  
		  scheduler.start();
	}
}
