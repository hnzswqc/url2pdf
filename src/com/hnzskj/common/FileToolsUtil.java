package com.hnzskj.common;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class FileToolsUtil {
	
	/**
	 * 将content写入file，默认是utf-8编码，覆盖写
	 * @param path    路径
	 * @param name    名称，带后缀
	 * @param content 内容
	 * @return 1        正常
	 *         0   UnsupportedEncodingException
	 *        -1   FileNotFoundException
	 *        -2   IOException
	 */
	public static int string2file(String path, String name, String content) {
		return string2file(path, name, content, "utf-8");
	}
	
	/**
	 * 将content写入file，覆盖写
	 * @param path    路径
	 * @param name    名称，带后缀
	 * @param content 内容
	 * @param charsetName 编码
	 * @return 1        正常
	 *         0   UnsupportedEncodingException
	 *        -1   FileNotFoundException
	 *        -2   IOException
	 */
	public static int string2file(String path, String name, String content, String charsetName) {
		return string2file(path, name, content, charsetName, false);
	}
	
	/**
	 * 将content写入file，覆盖写
	 * @param path    路径
	 * @param name    名称，带后缀
	 * @param content 内容
	 * @param charsetName 编码
	 * @param append 是否追加
	 * @return 1        正常
	 *         0   UnsupportedEncodingException
	 *        -1   FileNotFoundException
	 *        -2   IOException
	 */
	public static int string2file(String path, String name, String content, String charsetName, boolean append) {
		int flag = 1;
		String filename = path + name;
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append), charsetName));
			bw.write(content);
			bw.close();
			flag = 1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			flag = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag = -1;
		} catch (IOException e) {
			e.printStackTrace();
			flag = -2;
		}
		
		return flag;
	}
	
	
	/**
	 * 
	 * 方法描述：<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午2:07:25<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static String file2string(File file) {
		return file2string(file, "gbk");
	}
	
	/**
	 * 
	 * 方法描述：<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午2:07:33<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static String file2string(File file, String charset) {

		String output = "";
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), charset));
			StringBuffer buffer = new StringBuffer();
			int oneChar;

			while ((oneChar = input.read()) != -1) {
				buffer.append((char)oneChar);
			}
				
			output = buffer.toString();

			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
	
	
	/**
	 * 
	 * 方法描述：<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午2:07:44<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static String file2string(String path, String charset) {

		String output = "";
		File file = new File(path);
		
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), charset));
			StringBuffer buffer = new StringBuffer();
			int oneChar;

			while ((oneChar = input.read()) != -1) {
				buffer.append((char)oneChar);
			}

			output = buffer.toString();

			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
	
	/**
	 * 
	 * 方法描述：移动文件<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午2:08:03<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static int moveFile(String _srcFile, String _dstFile, String srcCharset, String dstCharset) {
		int flag = 1;
		
		File srcFile = new File(_srcFile);

		try {
			
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcFile), srcCharset));
			
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(_dstFile, true), dstCharset));
			
			String oneLine;
			while ((oneLine = input.readLine()) != null) {
				output.write(oneLine + "\n");
			}
			
			input.close();
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
			flag = 0;
		}
		
		srcFile.delete();
		
		return flag;
	}
	
	/**
	 * 将str中的非法文件名字符替换成空格
	 * @param str
	 * @return
	 */
	public static String toValidfilename(String str) {
		Pattern filePattern = Pattern.compile("[\\\\/:*?\"<>|]");
		String ret = "";
		
		if (str == null) {
			System.err.println("filename is null...");
			return "";
		} else {
			ret = filePattern.matcher(str).replaceAll(" ");
		}
		return ret;
	}
	
	/**
	 * 
	 * 方法描述：获取文件后缀名<br/>
	 * 创建人：King   <br/>
	 * 创建时间：2017年7月20日 下午4:14:00<br/>         
	 * @param <br/>   
	 * @return <br/>   
	 * @version   1.0<br/>
	 */
	public static String getExtName(String filePath) {
		String extName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		return extName;
    }

	
	
	public static void main(String[] args) {
//		System.out.println(FileToolsUtil.moveFile("d:\\response.xml", "d:\\log\\resp.xml", "gbk", "utf-8"));
		//System.out.println(FileToolsUtil.file2string("d:\\1111.txt", "gbk"));
		System.out.println(getExtName("aaa.sadas.asda阿萨德111、、、//.png"));
		
		
	}

}
