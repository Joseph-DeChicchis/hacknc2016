import java.util.ArrayList;
import java.util.List;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class Scraper {
	public static void main(String[] args){
		String website = "http://www.intern.supply/"; //enter the website to be scraped
		
		try {
			List<InternshipLink> links = getLinks(website);
		} catch (JauntException e) {
			e.printStackTrace();
		}
	}
	
	private static List<InternshipLink> getLinks(String site) throws JauntException {
		List<InternshipLink> links = new ArrayList<>();
		UserAgent agent = new UserAgent();
		agent.visit(site);
		
		Element div = agent.doc.findFirst("<div class=inner>");
		Elements ul = div.findEach("<li>");
		
		int len = ul.size();
		for(int i = 0; i < len; i++){
			Element li = ul.getElement(i);
			Element child = li.getChildElements().get(0);
			
			if(child.getName().equals("a")){
				String company = li.getText();
				String link = child.getAt("href");
				
				links.add(new InternshipLink(company, link));
			}
	
		}
		return links;	
	}
}
