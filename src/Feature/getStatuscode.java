package Feature;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class getStatuscode {
	private  String htmlUrl;
	private String code;
	public getStatuscode(String htmlUrl)
	{
		this.htmlUrl=htmlUrl;
	}
	public String getstatuscode()
	{
		if(htmlUrl==null)
		{
			System.err.println("查询网址不能为空！");

		}
		try
		{
			URL url=new URL(htmlUrl);
			 URLConnection rulConnection   = url.openConnection();
			 HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
			 httpUrlConnection.connect();
			 code = new Integer(httpUrlConnection.getResponseCode()).toString();
			 
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return code;
	}

}
