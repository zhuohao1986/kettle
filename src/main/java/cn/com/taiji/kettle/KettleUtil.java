package cn.com.taiji.kettle;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class KettleUtil {

	/**
	 * 调用trans文件
	 * 
	 * @param transFileName
	 * @throws Exception
	 */
	public static void callNativeTrans(String transFileName) throws Exception {
		callNativeTransWithParams(null, transFileName);
	}

	/**
	 * 调用trans文件 带参数的
	 * 
	 * @param params
	 * @param transFileName
	 * @throws Exception
	 */
	public static void callNativeTransWithParams(String[] params, String transFileName) throws Exception {
		// 初始化
		KettleEnvironment.init();
		EnvUtil.environmentInit();
		TransMeta transMeta = new TransMeta(transFileName);
		// 转换
		Trans trans = new Trans(transMeta);
		// 执行
		trans.execute(params);
		// 等待结束
		trans.waitUntilFinished();
		// 抛出异常
		if (trans.getErrors() > 0) {
			throw new Exception("There are errors during transformation exception!(传输过程中发生异常)");
		}
	}

	/**
     * 调用job文件
     * @param jobName
     * @throws Exception
     */
    public static void callNativeJob(String jobName) throws Exception{
          // 初始化 
          KettleEnvironment.init();
       
          JobMeta jobMeta = new JobMeta(jobName,null);
          Job job = new Job(null, jobMeta);  
          //向Job 脚本传递参数，脚本中获取参数值：${参数名}  
          //job.setVariable(paraname, paravalue);  
           job.start();  
           job.waitUntilFinished();  
           if (job.getErrors() > 0) {
           throw new Exception("There are errors during job exception!(执行job发生异常)"); 
           }  
    }
}
