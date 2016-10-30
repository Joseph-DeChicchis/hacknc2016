import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;


public class Scraper {
	public static void main(String[] args){
		String website = "http://www.intern.supply/"; //enter the website to be scraped
		String websiteDesign = "http://www.intern.supply/design.html";
		
		JSONObject data = new JSONObject();
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new File("data/db.json"));
			pw.println(data.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<InternshipLink> designLinks = getLinks(websiteDesign);
			int count = 0;
			for(InternshipLink link : designLinks){
				link.setLangauges(new HashSet<String>());
				link.setLocation(getLocation(link));
				link.setPositions(new HashSet<String>(Arrays.asList("UI")));
				link.setSize(getCompanySize(link));
				link.setPlatform("media");
				
				JSONObject company = getJSONObject(link);
				System.out.println(++count + " of " + designLinks.size());
				//data.wrap(company);
				pw.println(company + ",");
			}
			
			count = 0;
			List<InternshipLink> links = getLinks(website);
			for(InternshipLink link : links){
				link.setLangauges(getLanguageSkills(link));
				link.setLocation(getLocation(link));
				link.setPositions(getPositions(link));
				link.setSize(getCompanySize(link));
				link.setPlatform(getPlatform(link));
				
				JSONObject company = getJSONObject(link);
				System.out.println(++count + " of " + links.size());
				//data.wrap(company);
				pw.println(company + ",");
			}
			
		} catch (JauntException e) {
			e.printStackTrace();
		}
		
		System.out.println(data);
		
	}
	
	private static JSONObject getJSONObject(InternshipLink link){
		JSONObject company = new JSONObject();
		
		company.put("company", link.getCompany());
		company.put("link", link.getLink());
		company.put("size", link.getSize());
		company.put("platform", link.getPlatform());
		company.put("locations", new JSONArray(link.getLocation()));
		company.put("positions", new JSONArray(link.getPositions()));
		company.put("languages", new JSONArray(link.getLangauges()));
		
		return company;
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
		try{
			agent.visit(link.getLink());
		}catch(Exception e){
			return new HashSet<String>();
		}
		
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
		try{
			agent.visit(link.getLink());
		}catch(Exception e){
			return new HashSet<String>();
		}
		
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
		try{
			agent.visit(link.getLink());
		}catch(Exception e){
			return new HashSet<String>();
		}
		
		Element doc = agent.doc;
		
		Pattern p = Pattern.compile(Constants.getPositionString());
		Matcher m = p.matcher(doc.innerHTML().toLowerCase());
		
		while(m.find()) {
			String skill = m.group().trim().toLowerCase();
			positions.add(Constants.types.get(skill));
		}
		
		return positions;
	}
	
	public static String getPlatform(InternshipLink link) throws ResponseException{
		Set<String> positions = new HashSet<>();
		UserAgent agent = new UserAgent();
		try{
			agent.visit(link.getLink());
		}catch(Exception e){
			return "all";
		}
		
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
