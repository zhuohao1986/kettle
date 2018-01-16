package cn.com.taiji.kettle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KettleTaskJob {
	private static final Logger log = LoggerFactory.getLogger(KettleTaskJob.class);

	public void run() throws Exception {
		log.info("*****kettle定时任务运行开始******");
		String transFileName = "D:/soft/pdi-ce-6.0.1.0-386_/data-integration/zhuohao.ktr";
		KettleUtil.callNativeTrans(transFileName);
		log.info("*****kettle定时任务运行结束******");
	}

	public static void main(String[] args) throws Exception {
		KettleTaskJob job = new KettleTaskJob();
		job.run();
	}
}
