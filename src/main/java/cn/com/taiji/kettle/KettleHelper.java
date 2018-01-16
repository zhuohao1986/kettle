package cn.com.taiji.kettle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
  
public class KettleHelper {  
    /** 
     * 默认的密码字符串组合。用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合 
     */  
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    private static MessageDigest messagedigest = null;  
    static {  
        try {  
            messagedigest = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException nsaex) {  
            System.err.println("MD5Util.class.getName()" + "初始化失败，MessageDigest不支持MD5Util。");  
            nsaex.printStackTrace();  
        }  
    }  
      
    /** 
     * 生成字符串的md5校验值 
     *  
     * @param s 
     * @return 
     */  
    private static String getMD5String(String s) {  
        return getMD5String(s.getBytes());  
    }  
      
    /** 
     * 推断字符串的md5校验码是否与一个已知的md5码相匹配 
     *  
     * @param password 要校验的字符串 
     * @param md5PwdStr 已知的md5校验码 
     * @return 
     */  
    public static boolean checkPassword(String password, String md5PwdStr) {  
        String s = getMD5String(password);  
        return s.equals(md5PwdStr);  
    } 
    
    /** 
     * 生成文件的md5校验值 
     *  
     * @param file 
     * @return 
     * @throws IOException 
     */  
    public static String getFileMD5String(String fileName) throws IOException { 
    	File file = new File(fileName);
        InputStream fis;  
        fis = new FileInputStream(file);  
        byte[] buffer = new byte[1024];  
        int numRead = 0;  
        while ((numRead = fis.read(buffer)) > 0) {  
            messagedigest.update(buffer, 0, numRead);  
        }  
        fis.close();  
        return bufferToHex(messagedigest.digest());  
    }
    
    /** 
     * 获取文件的记录条数 
     *  
     * @param file 
     * @return 
     * @throws IOException 
     */  
    public static int getFileRecordCount(String fileName,boolean hasHeadRow) throws IOException { 
    	
    	File inFile = new File(fileName); // 读取的CSV文件
        @SuppressWarnings("unused")
		String inString = "";
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            while((inString = reader.readLine())!= null){
            	count++; 
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("没找到文件！");
        } catch (IOException ex) {
            System.out.println("读写文件出错！");
        }
    	if(hasHeadRow)
    	{
    		count--;
    	}
    	return count; 
    }
    
    /** 
     * 生成文件的md5校验值 
     *  
     * @param file 
     * @return 
     * @throws IOException 
     */  
    public static String getFileMD5String(File file) throws IOException {         
        InputStream fis;  
        fis = new FileInputStream(file);  
        byte[] buffer = new byte[1024];  
        int numRead = 0;  
        while ((numRead = fis.read(buffer)) > 0) {  
            messagedigest.update(buffer, 0, numRead);  
        }  
        fis.close();  
        return bufferToHex(messagedigest.digest());  
    }
  
    private static String getMD5String(byte[] bytes) {  
        messagedigest.update(bytes);  
        return bufferToHex(messagedigest.digest());  
    }  
  
    private static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }  
  
    private static String bufferToHex(byte bytes[], int m, int n) {  
        StringBuffer stringbuffer = new StringBuffer(2 * n);  
        int k = m + n;  
        for (int l = m; l < k; l++) {  
            appendHexPair(bytes[l], stringbuffer);  
        }  
        return stringbuffer.toString();  
    }  
  
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同   
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换   
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    } 
    
    
    /** 
     * 获取文个把的字节数
     * @param fileName
     * @return
     * @throws IOException
     */
    public static long getFileByteCount(String fileName) throws IOException
    {
    	File file = new File(fileName);
    	return file.length();
    }

    
}
