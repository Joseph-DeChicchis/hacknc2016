import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class Constants {
	public static String[] languages;
	
	public static Set<String> languagesSet;
	public static String[] positions;
	public static String[] requirementsKeywords;
	public static String[] mobileKeywords;
	public static String[] webKeywords;
	public static String[] largeCompanies;
	public static Set<String> largeCompaniesSet;
	public static Set<String> cities;
	
	static{
		largeCompanies = new String[]{"amazon", "amd", "apple", "broadcom", "cisco", "dell", "ebay", "emc"
				, "facebook", "featured","google", "hp", "ibm", "intel", "microsoft", "motorola", "netapp"
				, "oracle", "qualcomm", "symantec", "xerox", "yahoo"};
		mobileKeywords = new String[]{"mobile", "ios", "android"};
		webKeywords = new String[]{"front-end", "frontend", "back-end","backend", "sql"};
		requirementsKeywords = new String[]{"experience", "require"};
		cities = getCities("data/cities.txt");
		languages = new String[]{"java", "javascript", "php", "python", "objective-c"
				, "ruby", "perl", "c\\+\\+", "c#", "swift", "sql", "haskell", "scala", "bash"
				, "lua", "clojure", "assembly"};
		positions = new String[]{"android engineer", "software engineer in quality"
				, "product manager", "software engineer", "product management", "software developer", "big data engineer"
				, "quality assurence"};
		languagesSet = new HashSet<>(Arrays.asList(languages));
		largeCompaniesSet = new HashSet<>(Arrays.asList(largeCompanies));
	}
	
	public static String getLanguagesString(){
		StringBuffer sb = new StringBuffer("(");
		for(String skill : languages){		
			sb.append("|" + skill);
		}
		
		sb.replace(1,  2, "");
		sb.append(")");
		
		return sb.toString();
	}
	
	public static String getMobileString(){
		StringBuffer sb = new StringBuffer("(");
		for(String tech : mobileKeywords){		
			sb.append("|" + tech);
		}
		
		sb.replace(1,  2, "");
		sb.append(")");
		
		return sb.toString();
	}
	
	
	public static String getWebString(){
		StringBuffer sb = new StringBuffer("(");
		for(String tech : webKeywords){		
			sb.append("|" + tech);
		}
		
		sb.replace(1,  2, "");
		sb.append(")");
		
		return sb.toString();
	}
	
	public static String getPositionString(){
		StringBuffer sb = new StringBuffer("(");
		for(String pos : positions){		
			sb.append("|" + pos);
		}
		
		sb.replace(1,  2, "");
		sb.append(")");
		
		return sb.toString();
	}
	
	private static Set<String> getCities(String path){
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		Set<String> set = new TreeSet<>();
		String city;
		
		try {
			while((city = br.readLine()) != null){
				set.add(city.trim().toLowerCase());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return set;
	}
}
