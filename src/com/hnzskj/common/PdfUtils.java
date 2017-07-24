/*
 * @项目名称: UrlToPdf
 * @文件名称: PdfUtils.java
 * @日期: 2017年7月20日 下午5:34:15  
 * @版权: 2017 河南中审科技有限公司
 * @开发公司或单位：河南中审科技有限公司研发部
 */
package com.hnzskj.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**    
 * 项目名称：UrlToPdf   <br/>
 * 类名称：PdfUtils.java   <br/>
 * 类描述：生成pdf工具类   <br/>
 * 创建人：King   <br/>
 * 创建时间：2017年7月20日 下午5:34:15   <br/>
 * 修改人：开发部笔记本   <br/>
 * 修改时间：2017年7月20日 下午5:34:15   <br/>
 * 修改备注：    <br/>
 * @version  1.0  
 */
public class PdfUtils {
	
	/**
	 * 生成图片的后缀名
	 */
	private String EXTNAME=".png";
	
	/**
	 * 生成pdf的后缀名
	 */
	private String PDF_EXTNAME=".pdf";
	
	/**
	 * 
	 * 方法描述：生成pdf文件流<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午5:43:02<br/>         
	 * @param url 要转换的url路径，必须加http://<br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public FileInputStream convertToPdf(String url,String pdfPath){
		String random = UUID.randomUUID().toString();
		String filePath=getLocalDisk().concat("://").concat(random).concat(EXTNAME);
		//String pdfPath = getLocalDisk()+"://"+random+".pdf";
		int result = ImplementsCmd.buildFile(url, filePath);
		FileInputStream fis = null;
		if(result==ImplementsCmd.SUCCESS){
			fis = ImplementsCmd.convert(filePath,pdfPath);
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
		}
		return fis;
	}
	
	
	/**
	 * 
	 * 方法描述：生成pdf文件流<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午5:43:02<br/>         
	 * @param url 要转换的url路径，必须加http://<br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public FileInputStream convertToPdf2(String url,String pdfPath){
		String random = UUID.randomUUID().toString();
		//String pdfPath=getLocalDisk().concat("://").concat(random).concat(PDF_EXTNAME);
		//String pdfPath = getLocalDisk()+"://"+random+".pdf";
		int result = ImplementsCmd.buildFile(url, pdfPath);
		
		FileInputStream fis = null;
		if(result==ImplementsCmd.SUCCESS){
			try {
				File file = new File(pdfPath);
				if(file.exists()){
					fis = new FileInputStream(file);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return fis;
	}
	
	
	/**
	 * 
	 * 方法描述：生成pdf文件流<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午5:43:02<br/>         
	 * @param url 要转换的url路径，必须加http://<br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 * @throws FileNotFoundException 
	 */
	public FileInputStream convertToImg(String url) {
		String random = UUID.randomUUID().toString();
		String filePath=getLocalDisk().concat("://").concat(random).concat(EXTNAME);
		FileInputStream fis = null;
		try{
			int result = ImplementsCmd.buildFile(url, filePath);
			if(result == ImplementsCmd.SUCCESS){
				File file = new File(filePath);
				if(file.exists()){
					fis = new FileInputStream(file);
					//删除文件
					file.delete();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return fis;
	}
	
	
	/**
	 * 
	 * 方法描述：输入流转byte<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午6:55:48<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	private byte[] fileInputStreamToByte(FileInputStream fis){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = null;
		try {
			buffer = new byte[fis.available()];
			fis.read(buffer);
			out.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fis!=null){
				fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}
	
	/**
	 * 
	 * 方法描述：获取当前程序执行的磁盘<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午7:22:22<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	private String getLocalPath(){
		String className = getClass().getName();
		String packageName="";
		if(null!=getClass().getPackage()){
			packageName = getClass().getPackage().getName();
		}
		String classFileName="";
		if(!"".equals(packageName)){
			classFileName = className.substring(packageName.length()+1, className.length());
		}else{
			classFileName = className;
		}
		URL url = null;
		url = getClass().getResource(classFileName+".class");
		String strUrl=url.toString();
		strUrl = strUrl.substring(strUrl.indexOf("/")+1,strUrl.lastIndexOf("/")).replaceAll("%20", "");
		return strUrl;
	}
	
	/**
	 * 
	 * 方法描述：获取当前磁盘<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午7:28:54<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public String getLocalDisk(){
		String path = getLocalPath();
		return path.substring(0,1);
	}
	
	public void test1(){
		PdfUtils pdfUtils = new PdfUtils();
		try {
			String path="d://xdoc//1.png";
			String url="http://www.jb51.net/article/48817.htm";
			FileOutputStream fos = new FileOutputStream(new File(path));
			FileInputStream fis = pdfUtils.convertToImg(url);
			 byte[] bytes = fileInputStreamToByte(fis);
			 fos.write(bytes);
			 fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		PdfUtils pdfUtils = new PdfUtils();
		//System.err.println(pdfUtils.getLocalPath()+"~~~~~");
		pdfUtils.test1();
		System.out.println("执行结束...");
	}
	
	
}
