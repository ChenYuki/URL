package Feature;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
public class FileUtil {
	private static String path;

	public FileUtil()
	{
		path="src\\Dataset\\1.txt";
		
	}
	public static List<String> readFile()
	{
		List<String> content=new ArrayList<String>();
		try
		{
			Reader areader = new InputStreamReader(new FileInputStream(path),"utf-8");
			BufferedReader bufferedreader=new BufferedReader(areader);
			//StringBuffer sb=new StringBuffer();
			String temp=null;
			while((temp=bufferedreader.readLine())!=null)
			{
				content.add(temp);
			}
			
			bufferedreader.close();	
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return content;
	}
	
	//向某一路径下写入文件
	public static boolean writeToFile(String content, String path) {
		boolean success = false;
		FileWriter fileWriter = null;
		boolean fileExisted = creatTXTFile(path);
		if (fileExisted) {
			//文件已存在
			try {
		        File file = new File(path);
		        fileWriter = new FileWriter(file, true);
		        fileWriter.write(content);  
		    } catch (Exception e) {
		    	e.printStackTrace();
		    } finally {
		    	try {
					fileWriter.flush();
			        fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		} else {
			//文件不存在(主要可能是创建失败),success默认为false无需处理
		}
		return success;
	}
	
	//根据路径创建TXT文件
	public static boolean creatTXTFile(String path) {
		boolean success = false;
        File file = new File(path);
		if(!file.exists()){
            try {
				file.createNewFile();
				success = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
			success = true;
		}
		return success;
	}
	
}
