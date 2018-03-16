package Feature;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Map.Entry;
public class HttpHeaders {
//	private  String htmlUrl;
	public HttpHeaders()
	{
	}
	public HttpHeaders(String htmlUrl)
	{
//		this.htmlUrl=htmlUrl;
	}
	//nowHopCount为传入的重定向跳数,用来方便递归
	public int getHeaders(String htmlUrl, int nowHopNumber)
	{
		if(htmlUrl==null)
		{
			System.err.println("查询网址不能为空！");
			return nowHopNumber;
		} else {
			try {
				System.out.println("----------------网站："+htmlUrl+"HTTP响应头---------------");
				URL url=new URL(htmlUrl);
				URLConnection connection = url.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
				//获取httpHeader信息
				int statusCode = httpURLConnection.getResponseCode();
				String responseMessage = httpURLConnection.getResponseMessage();
				String locationUrlString =  httpURLConnection.getHeaderField("Location");
				System.out.println("Response Headers:");  
		        System.out.println(" status: " + statusCode + " " + responseMessage);  
//		        System.out.println("重定向的网址为：" + httpURLConnection.getURL());
//		        System.out.println(" content-encoding: " + httpURLConnection.getContentEncoding());  
//		        System.out.println(" content-length : " + httpURLConnection.getContentLength());  
//		        System.out.println(" content-type: " + httpURLConnection.getContentType());  
//		        System.out.println(" Date: " + httpURLConnection.getDate());  
//		        System.out.println(" ConnectTimeout: " + httpURLConnection.getConnectTimeout());  
//		        System.out.println(" expires: " + httpURLConnection.getExpiration());  
//		        System.out.println(" content-type: " + httpURLConnection.getHeaderField("content-type"));//利用另一种读取HTTP头字段  
		        System.out.println();
		        
		        //判断状态码
		        if (statusCode/100 == 3) {
		        	//状态码为3xx，重定向
					if (!locationUrlString.equals(htmlUrl)) {
						//不同说明有location可用
				        System.out.println("重定向的网址为：" + locationUrlString);
						return getHeaders(locationUrlString, nowHopNumber) + 1;
					} else {
						//相同说明没有指定location，直接返回当前跳数
				        System.out.println("相同的网址为：" + locationUrlString);
						return nowHopNumber;
					}
				} else {
					//只关注状态码为3xx的情况，剩下的一律视为正常
					return nowHopNumber;
				}
		        
//				Map<String, List<String>> headerFields = connection.getHeaderFields();
//				Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
//				Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
//				while(iterator.hasNext())
//				{
//					Entry<String, List<String>> next = iterator.next();
//					String key=next.getKey();
//					List<String> value = next.getValue();
//					if(key==null)
//						System.out.println(value.toString());
//					else 
//						System.out.println(key+":"+value.toString());
//				}
//				System.out.println("");
		}catch(IOException e)
			{
			System.err.println("无法查询网址！");
			return nowHopNumber;
			}
		}
	}
}
