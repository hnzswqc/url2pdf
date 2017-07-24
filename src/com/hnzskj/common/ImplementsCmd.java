package com.hnzskj.common;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;


/**
 * 
 * 项目名称：UrlToPdf   <br/>
 * 类名称：ImplementsCmd.java   <br/>
 * 类描述：  url转图片 <br/>
 * 创建人：King   <br/>
 * 创建时间：2017年7月20日 下午4:04:15   <br/>
 * 修改人：开发部笔记本   <br/>
 * 修改时间：2017年7月20日 下午4:04:15   <br/>
 * 修改备注：    <br/>
 * @version  1.0
 */
public class ImplementsCmd {

	public static String[] imagesType = {"bmp","jpeg","png","gif","pdf"};
	
	public static Integer SUCCESS=1;
	public static Integer ERROR=0;
	public static Integer NOT_ALLOW = -1;

	public static String WEBROOT="";

	/**
	 * 
	 * 方法描述：调用命令执行<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午4:03:48<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static void  implcmd(String cmd) throws InterruptedException{//在java中调用执行cmd命令
		Process p = null;
		System.out.println(cmd);
		try {
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();//等待执行完毕再进行后续操作！
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void  reload(String url,String picname,String jsname){
		String content = "";
		String str = FileToolsUtil.file2string(WEBROOT+"/WEB-INF/classes/phantomjs/NetToPicMoban.js", "utf-8");
		String content1 = str.replace("url", "'"+url+"'");
		content = content1.replace("savename", "'"+picname+"'");
		//FileToolsUtil.string2file("src/phantomjs/", jsname, content);
		FileToolsUtil.string2file(WEBROOT+"/WEB-INF/classes/phantomjs/", jsname, content);
		
	}
	
	
	/**
	 * 
	 * 方法描述：把url链接地址生成图片<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午3:47:09<br/>         
	 * @param url 目标 url<br/>
	 * @param filePath 生成文件路径，服务器路径<br/>   
	 * @return -1 非图片格式<br/>
	 * @return 0 生成失败<br/>
	 * @return 1生成成功	<br/>   
	 * @version   1.0<br/>
	 */
	public static int buildFile(String url,String filePath){
		if(!isImage(filePath)){
			//非图片格式
			return NOT_ALLOW;
		}
		String jsname = "hnzs.js";
		ImplementsCmd.reload( url, filePath,jsname);
		String cmd1 = "cmd /c cd " +WEBROOT+"/WEB-INF/classes/phantomjs/" ;
		String cmd  = cmd1 + "&&phantomjs.exe " +jsname;
		try {
			ImplementsCmd.implcmd(cmd);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	
	
	/**
	 * 
	 * 方法描述：回去当前的盘符<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月21日 下午2:11:45<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static String getRootDisk(){
		String path = ImplementsCmd.WEBROOT;
		if(null==path||"".equals(path)){
			path="D";
		}
		return path.substring(0,1);
	}
	
	
	/**
	 * 
	 * 方法描述：把url链接地址生成图片<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午3:47:09<br/>         
	 * @param url 目标 url<br/>
	 * @param filePath 生成文件路径，服务器路径<br/>   
	 * @return -1 非图片格式<br/>
	 * @return 0 生成失败<br/>
	 * @return 1生成成功	<br/>   
	 * @version   1.0<br/>
	 * @throws FileNotFoundException 
	 */
	public static FileInputStream buildInputStreamFile(String url,String filePath) throws FileNotFoundException{
		int result = buildFile(url, filePath);
		if(result == SUCCESS){
			return new FileInputStream(new File(filePath));
		} 
		return null;
	}
	
	/**
	 * 
	 * 方法描述：验证传递过来的是否是正常的图片类型<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午4:02:44<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static boolean isImage(String filePath){
	   List<String> imageTyepLists=Arrays.asList(imagesType);
       if(imageTyepLists.contains(FileToolsUtil.getExtName(filePath).toLowerCase())){
            return true;
       }else{
           return false;
       }
	}
	
	
	/**
	 * 
	 * 方法描述：把图片转换成pdf，文件流<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午3:55:20<br/>         
	 * @param filePath 文件路径<br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static FileInputStream imgageToPdf(String filePath,String pdfPath){
		File file = new File(filePath);
		if(!file.exists()){
			return null;
		}
		return convert(filePath,pdfPath);
	}
	
	/**
	 * 
	 * 方法描述：生成的图片返文件流<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午4:51:31<br/>         
	 * @param filePath 要转换的图片，及通过url生成的图片<br/>   
	 * @param pdfPath 生成的pdf的路径<br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static FileInputStream convert(String filePath,String pdfPath){
		FileInputStream  ins = null;
		try {
			//要转换的图片路径
			File inFile = new File(filePath);
			BufferedImage img = ImageIO.read(inFile);
			FileOutputStream fos = new FileOutputStream(pdfPath);
			Document doc = new Document(null, 0, 0, 0, 0);
			doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
			Image image = Image.getInstance(filePath);
			PdfWriter.getInstance(doc, fos);
			doc.open();
			doc.add(image);
			doc.close();
			//生成的pwd路径
			File file = new File(pdfPath);
			ins =new FileInputStream(file);
			inFile.delete();//生成图片
			file.delete();//删除生成的pdf
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return ins;
	}
	
	
	
	
	
	public static void test(){
		String url = "http://www.yixieshi.com/88480.html" ;
		String picname = "D://xdoc//aa.gif" ;
		String jsname = "hnzs.js";
		ImplementsCmd.reload( url, picname,jsname);
		String cmd1 = "cmd /c cd" + " src/phantomjs/" ;
		String cmd  = cmd1 + "&&phantomjs.exe " + jsname;
		try {
			ImplementsCmd.implcmd(cmd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		ImplementsCmd.test();
		System.out.println("测试.......");
	}
}
