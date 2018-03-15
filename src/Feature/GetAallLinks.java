package Feature;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAallLinks {
	ArrayList<String> linklist=new ArrayList();
	public GetAallLinks()
	{
	}
	public ArrayList<String> getAllLink(Document doc,String url)
	{
		try
		{
			Element body = doc.body();
			Elements es=body.select("a");
			for (Iterator it = es.iterator(); it.hasNext();)
			{
				Element e = (Element) it.next();
				String links=e.attr("href");
				linklist.add(links);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return linklist;

	}
	
	
	

}
