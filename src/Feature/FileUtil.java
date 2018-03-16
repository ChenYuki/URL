package Feature;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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
}
