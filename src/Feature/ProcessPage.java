 package Feature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.http.HttpRequest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
public class ProcessPage {
	static List<String> setwarningMeta=new ArrayList<String>();
	static List<String> setwarningJSRedirect =new ArrayList<String>();
	static List<String> setIframedirectUrl=new ArrayList<String>();
	static ArrayList<String> linklist=new ArrayList();
	static List<ArrayList> setwarningLink=new ArrayList<ArrayList>();
	public static void processpage() throws Exception
	{
		FileUtil fileutil=new FileUtil();
		List<String> URLList=new ArrayList<String>();
		URLList=fileutil.readFile();
		Document doc=null;
		Iterator it=URLList.iterator();
		String url;
		while(it.hasNext())
		{
			try
			{
				url=(String)it.next();
				String urldomain=getUrlDomainName(url);
				
				HttpHeaders httpheader=new HttpHeaders();
				int hopNumber = httpheader.getHeaders(url, 0);
				System.out.println("重定向跳数为：" + hopNumber);
				
				doc=Jsoup.connect(url).get();
				GetAallLinks links=new GetAallLinks();
				linklist=links.getAllLink(doc,url);
				ArrayList<String> tempList = new ArrayList();
				for(int i=0;i<linklist.size();i++)
				{
					String tempdomain=getUrlDomainName(linklist.get(i));
					if(tempdomain!=null)
					{
						if(tempdomain.contains(":"))
						{
							String []tempdomain1=tempdomain.split(":");
							if(compareDomainName(tempdomain1[0],urldomain))
								tempList.add(linklist.get(i));		
						}
						else
							if(compareDomainName(tempdomain,urldomain))
						{
								tempList.add(linklist.get(i));
						}		
					}
				}
				System.out.println("-----");
				for (int i = 0; i< tempList.size();i++) {
					System.out.println(tempList.get(i));
				}
				System.out.println("-----");
				setwarningLink.add(tempList);
				if(checkJSRedirections(doc,url))
				{
					System.out.println("JSwarningFlag=1");
				}
				else
					System.out.println("JSwarningFlag=0");
				if(checkMetaRedirections(doc,url))
				{
					System.out.println("MetawarningFlag=1");
				}
				else
					System.out.println("MetawarningFlag=0");
				if(checkIframeRedirections(doc,url))
				{
					System.out.println("IframewarningFlag=1");
				}
				else
					System.out.println("IframewarningFlag=0");
				
			}catch(Exception e1)
			{
				System.out.println(e1.getMessage().toString());
			}
		}
		
		
	}
	private static boolean checkIframeRedirections(Document doc, String url) 
	{
		Elements Iframe=doc.select("iframe");
		if(Iframe!=null)
		{
			String border=Iframe.attr("frameborder");
			if(border!=null&&border=="0")
			{
				String src=Iframe.attr("src");
				if(src!=null)
				{
					String domainName=getUrlDomainName(url);
					if(domainName!=null)
					{
						String src_domianName=getUrlDomainName(src);
						if(compareDomainName(src_domianName,domainName))
						{
							setIframedirectUrl.add(src);
							return true;
						}	
					}	
				}
			}
		}
		return false;
		
		// TODO Auto-generated method stub
		
	}
	public static boolean checkJSRedirections(Document doc,String url)
	{
		int relocationCounts=0;
		String docStr=doc.toString();
		if(docStr.contains("window.open"))
		{
			relocationCounts++;
		}
		if(docStr.contains("window.showModaLDiaLog"))
		{
			relocationCounts++;
		}
		if(docStr.contains("window.Location.assign"))
		{
			relocationCounts++;
		}
		if(docStr.contains("window.navigate"))
		{
			relocationCounts++;
		}
		if(docStr.contains("self.Location"))
		{
			relocationCounts++;
		}
		if(docStr.contains("top.Location"))
		{
			relocationCounts++;
		}
		if(docStr.contains(".click()"))
		{
			relocationCounts++;
		}
		if(relocationCounts>0)
		{
			Pattern p=Pattern.compile("(http://)(www\\.)?([a-zA-Z0-9]+)\\.[a-zA-Z0-9]\\*\\.[a-z]{3}\\.?([a-z]+?)");
			Elements es=doc.select("script");
			for(Element e:es)
			{
				Matcher m=p.matcher(e.html());
				while(m.find())
				{
					String redirectURL=m.group();
					String domainNameRedirect=getUrlDomainName(redirectURL);
					String domainName=getUrlDomainName(url);
					if(domainNameRedirect!=null)
					{
						if(compareDomainName(domainNameRedirect,domainName))
						{
							setwarningJSRedirect.add(redirectURL);
							return true;
						}
					}
					
				}
				
			}
		}
		return false;
	}
	public static boolean checkMetaRedirections(Document doc,String url)
	{
		 String http_equiv;
		 String meta_refresh;
		 Elements meta=doc.select("meta");
		 if(meta!=null)
		 {
			 String lvHttpEquiv=meta.attr("http-equiv");
			 if(lvHttpEquiv!=null&&lvHttpEquiv.toLowerCase().contains("refresh"))
			 {
				 String lvContent=meta.attr("content");
				 if(lvContent!=null)
				 {
					 String[] lvContentArray=lvContent.split(";");
					 if(lvContentArray.length>1)
					 {
						 http_equiv=lvContentArray[1];
						 meta_refresh=lvContentArray[0];
						 int meta_refresh_time=0;
						 String http_equiv_url=http_equiv.substring(4);
						 String http_equiv_url_domainName=getUrlDomainName(http_equiv_url);
						 if(http_equiv_url_domainName!=null)
						 {
							 String domainName=getUrlDomainName(url);
							 try
							 {
								  meta_refresh_time=Integer.parseInt(meta_refresh);
							 }
							 catch(NumberFormatException e)
							 {
								 e.printStackTrace();
							 }
							 if((compareDomainName(http_equiv_url_domainName,domainName))&&(meta_refresh_time)<5)
							 {
								 setwarningMeta.add(http_equiv_url);
								 return true;
							 }	 
						 } 
						 
					 }
				 }
			 }
		 }
		return false;
	}
	public static String getUrlDomainName(String htmlurl)
	{
		
		Pattern p=Pattern.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62}|(:[0-9]{1,4}))+\\.?");
		Matcher m=p.matcher(htmlurl);
		if(m.find())
		{
			return m.group();
		}
		return null;
	}
	public static boolean compareDomainName(String url1,String url2)
	{
		if(url1.equals(url2))
		{
			return false;
		}
		return true;
	}
		
	public ProcessPage()
	{
		System.out.println("------------开始读取网页-----------");
	}
}
