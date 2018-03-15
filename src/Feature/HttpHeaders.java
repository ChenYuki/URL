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
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
			Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
			while(iterator.hasNext())
			{
				Entry<String, List<String>> next = iterator.next();
				String key=next.getKey();
				List<String> value = next.getValue();
				if(key==null)
					System.out.println(value.toString());
				else 
					System.out.println(key+":"+value.toString());
		}
			System.out.println("");
	}catch(IOException e)
		{
		System.err.println("无法查询网址！");
		}
	}
}
