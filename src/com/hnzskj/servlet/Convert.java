/*
 * @项目名称: UrlToPdf
 * @文件名称: Convert.java
 * @日期: 2017年7月20日 下午7:43:36  
 * @版权: 2017 河南中审科技有限公司
 * @开发公司或单位：河南中审科技有限公司研发部
 */
package com.hnzskj.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hnzskj.common.ImplementsCmd;
import com.hnzskj.common.PdfUtils;

/**    
 * 项目名称：UrlToPdf   <br/>
 * 类名称：Convert.java   <br/>
 * 类描述：转换的servlet   <br/>
 * 创建人：King   <br/>
 * 创建时间：2017年7月20日 下午7:43:36   <br/>
 * 修改人：开发部笔记本   <br/>
 * 修改时间：2017年7月20日 下午7:43:36   <br/>
 * 修改备注：    <br/>
 * @version  1.0  
 */
public class Convert extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5726200609837554450L;

	/**
		 * Constructor of the object.
		 */
	public Convert() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		if(null==url){
			return;
		}
		String webroot = request.getSession().getServletContext().getRealPath("");
		ImplementsCmd.WEBROOT = webroot;
		String random = UUID.randomUUID().toString();
		String pdfPath = webroot.substring(0,1)+"://"+random+".pdf";
		PdfUtils pdfUtils = new PdfUtils();
		FileInputStream fileInputStream = pdfUtils.convertToPdf2(url,pdfPath);
		String fileName=getFileName();		
		 // 写明要下载的文件的大小  
        response.setContentLength((int) fileInputStream.available());  
        response.setHeader("Content-Disposition", "attachment;filename="  
                + fileName);// 设置在下载框默认显示的文件名  
        response.setContentType("application/octet-stream");// 指明response的返回对象是文件流  
        // 读出文件到response  
        // 这里是先需要把要把文件内容先读到缓冲区  
        // 再把缓冲区的内容写到response的输出流供用户下载  
        BufferedInputStream bufferedInputStream = new BufferedInputStream(  
                fileInputStream);  
        byte[] b = new byte[bufferedInputStream.available()];  
        bufferedInputStream.read(b);  
        OutputStream outputStream = response.getOutputStream();  
        outputStream.write(b);  
        // 人走带门  
        bufferedInputStream.close();  
        outputStream.flush();  
        outputStream.close();  
        File pdfPathFile = new File(pdfPath);
		if(pdfPathFile.exists()){
			pdfPathFile.delete();
			System.out.println("pdf删除："+pdfPath);
		}
	}
	
	/**
	 * 
	 * 方法描述：设置文件名称<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月21日 上午11:29:58<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	private String getFileName(){
		java.util.Date date = new java.util.Date();
		// 时间的显示格式是yyyy-MM-dd- HH:mm:ss
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String data = format.format(date);
		return data.concat(".pdf");
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}

}
