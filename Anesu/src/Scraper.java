import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class Scraper {
	public static void main(String[] args){
		String website = "http://www.intern.supply/"; //enter the website to be scraped
		
		try {
			List<InternshipLink> links = getLinks(website);
			links.stream().forEach(a -> System.out.println(a));
			System.out.println(getLocation(links.get(3)));
			System.out.println(getLanguageSkills(links.get(3)));
			System.out.println(getPositions(links.get(4)));
			System.out.println(getPlatform(links.get(9)));
			System.out.println(links.get(6).getCompany());
			System.out.println(getCompanySize(links.get(6)));
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
	
	private static Set<String> getLocation(InternshipLink link) throws ResponseException{
		Set<String> locations = new HashSet<>();
		UserAgent agent = new UserAgent();
		agent.visit(link.getLink());
		
		Element doc = agent.doc;
		Pattern p = Pattern.compile("[A-Z][a-zA-Z]{1,15}(\\.)?(\\s){0,2}([A-Z][a-zA-Z]{1,21})?,(\\s)?(([A-Z]{2})|(United States)|(US))");
		Matcher m = p.matcher(doc.innerHTML());
		
		while(m.find()) {
			String loc = m.group();
			if(isValidLocation(loc))
				locations.add(loc);
		}
		return locations;
	}
	
	private static Set<String> getLanguageSkills(InternshipLink link)throws ResponseException {
		Set<String> skills = new HashSet<>();
		UserAgent agent = new UserAgent();
		agent.visit(link.getLink());
		
		Element doc = agent.doc;
		Pattern p = Pattern.compile(Constants.getLanguagesString());
		Matcher m = p.matcher(doc.innerHTML().toLowerCase());
		
		while(m.find()) {
			String skill = m.group().trim().toLowerCase();
			if(Constants.languagesSet.contains(skill))
				skills.add(skill);
		}
		
		return skills;
	}
	
	private static Set<String> getRequirements(InternshipLink link) throws ResponseException, NotFound{
		Set<String> skills = new HashSet<>();
		UserAgent agent = new UserAgent();
		agent.visit(link.getLink());
		
		Element doc = agent.doc;

		
		return null;
	}
	
	private static Set<String> getPositions(InternshipLink link) throws ResponseException, NotFound{
		Set<String> positions = new HashSet<>();
		UserAgent agent = new UserAgent();
		agent.visit(link.getLink());
		
		Element doc = agent.doc;
		
		Pattern p = Pattern.compile(Constants.getPositionString());
		Matcher m = p.matcher(doc.innerHTML().toLowerCase());
		
		while(m.find()) {
			String skill = m.group().trim().toLowerCase();
			positions.add(skill);
		}
		
		return positions;
	}
	
	public static String getPlatform(InternshipLink link) throws ResponseException{
		Set<String> positions = new HashSet<>();
		UserAgent agent = new UserAgent();
		agent.visit(link.getLink());
		
		int mobile = 0;
		int web = 0;
		
		Pattern p = Pattern.compile(Constants.getMobileString());
		Matcher m = p.matcher(agent.doc.innerHTML().toLowerCase());
		
		while(m.find()){
			mobile++;
		}
		
		p = Pattern.compile(Constants.getWebString());
		m = p.matcher(agent.doc.innerHTML().toLowerCase());
		
		while(m.find()){
			web++;
		}
		
		if(mobile > web){
			return "mobile";
		}else{
			return "web";
		}
	}
	
	public static String getCompanySize(InternshipLink link){
		String company = link.getCompany().toLowerCase();
		if(Constants.largeCompaniesSet.contains(company)){
			return "large";
		}
		return "medium";
	}
	
	private static boolean isValidLocation(String loc){
		String city = loc.split(",")[0].trim().toLowerCase();
		return Constants.cities.contains(city);
	}
}
