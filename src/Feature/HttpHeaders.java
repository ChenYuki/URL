package Feature;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import java.util.Map.Entry;
public class HttpHeaders {
	private  String htmlUrl;
	public HttpHeaders(String htmlUrl)
	{
		this.htmlUrl=htmlUrl;
	}
	public void getHeaders()
	{
		if(htmlUrl==null)
		{
			System.err.println("查询网址不能为空！");
		}
		try
		{
			System.out.println("----------------网站："+htmlUrl+"HTTP响应头---------------");
			URL url=new URL(htmlUrl);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
			
			int statusCode = httpURLConnection.getResponseCode();
			String responseMessage = httpURLConnection.getResponseMessage();
			URL locationUrl =  httpURLConnection.getURL();
			System.out.println("Response Headers:");  
	        System.out.println(" status: " + statusCode + " " + responseMessage);  
//	        System.out.println(" content-encoding: " + httpURLConnection.getContentEncoding());  
//	        System.out.println(" content-length : " + httpURLConnection.getContentLength());  
//	        System.out.println(" content-type: " + httpURLConnection.getContentType());  
//	        System.out.println(" Date: " + httpURLConnection.getDate());  
//	        System.out.println(" ConnectTimeout: " + httpURLConnection.getConnectTimeout());  
//	        System.out.println(" expires: " + httpURLConnection.getExpiration());  
//	        System.out.println(" content-type: " + httpURLConnection.getHeaderField("content-type"));//利用另一种读取HTTP头字段  
	        System.out.println();
	        
	        //判断状态码
	        if (statusCode/100 == 2) {
				//状态码为2xx，正常
		        System.out.println("当前网址为: " + locationUrl.toString());
			} else if (statusCode/100 == 3) {
				//状态码为3xx，重定向
		        System.out.println("重定向的网址为：" + locationUrl.toString());
			}
	        
//			Map<String, List<String>> headerFields = connection.getHeaderFields();
//			Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
//			Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
//			while(iterator.hasNext())
//			{
//				Entry<String, List<String>> next = iterator.next();
//				String key=next.getKey();
//				List<String> value = next.getValue();
//				if(key==null)
//					System.out.println(value.toString());
//				else 
//					System.out.println(key+":"+value.toString());
//			}
//			System.out.println("");
	}catch(IOException e)
		{
		System.err.println("无法查询网址！");
		}
	}
}
