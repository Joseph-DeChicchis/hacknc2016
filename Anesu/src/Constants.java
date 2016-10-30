import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Constants {
	private static final String SOFTWARE_ENGINEERING = "SE";
	private static final String QUALITY_ASSURANCE = "QA";
	private static final String DESIGN = "UI";
	private static final String DATA_ANALYST = "DA";
	
	public static String[] languages;
	
	public static Set<String> languagesSet;
	public static String[] positions;
	public static String[] requirementsKeywords;
	public static String[] mobileKeywords;
	public static String[] webKeywords;
	public static String[] largeCompanies;
	public static String[] categories;
	public static String[] softwareKeyWords;
	public static String[] hardwareKeyWords;
	public static String[] realEstateKeyWords;
	public static String[] fincanceKeyWords;
	public static String[] entertainmentKeyWords;
	public static String[] travelKeyWords;
	public static Set<String> largeCompaniesSet;
	public static Set<String> cities;
	
	public static Map<String, String> types;
	
	static{
		softwareKeyWords = new String[]{"soft", "software", "internet"};
		hardwareKeyWords = new String[]{"hard", "hardware", "system"};
		categories = new String[]{"software", "hardware", "design", "finance", "real estate"
				, "entertainment", "travel"};
		largeCompanies = new String[]{"amazon", "amd", "apple", "broadcom", "cisco", "dell", "ebay", "emc"
				, "facebook", "featured","google", "hp", "ibm", "intel", "microsoft", "motorola", "netapp"
				, "oracle", "qualcomm", "symantec", "xerox", "yahoo"};
		mobileKeywords = new String[]{"mobile", "ios", "android"};
		webKeywords = new String[]{"front-end", "frontend", "back-end","backend", "sql"};
		requirementsKeywords = new String[]{"experience", "require"};
		cities = getCities("data/cities.txt");
		languages = new String[]{"java", "python", "objective-c"
				, "ruby", "perl", "c\\+\\+", "c#", "swift", "sql", "haskell", "bash"
				, "lua", "clojure", "assembly"};
		positions = new String[]{"android engineer"
				, "product manager", "software engineer", "product management","QA", "software developer", "big data engineer"
				, "quality assurance", "software design engineer", "sw engineer", "UI/UX", "tester", "statistic", "big data"
				, "software eng", "developer", "data scientist"};
		languagesSet = new HashSet<>(Arrays.asList(languages));
		largeCompaniesSet = new HashSet<>(Arrays.asList(largeCompanies));
		types = getTypes();
	}
	
	private static Map<String, String> getTypes(){
		Map<String, String> map = new HashMap<>();
		map.put("android engineer", SOFTWARE_ENGINEERING);
		map.put("software engineer", SOFTWARE_ENGINEERING);
		map.put("software engineer in quality", QUALITY_ASSURANCE);
		map.put("product management", SOFTWARE_ENGINEERING);
		map.put("product manager", SOFTWARE_ENGINEERING);
		map.put("sw engineer", SOFTWARE_ENGINEERING);
		map.put("UI/UX", DESIGN);
		map.put("quality assurance", QUALITY_ASSURANCE);
		map.put("software design engineer", SOFTWARE_ENGINEERING);
		map.put("sw engineer", SOFTWARE_ENGINEERING);
		map.put("big data engineer", DATA_ANALYST);
		map.put("QA", QUALITY_ASSURANCE);
		map.put("tester", QUALITY_ASSURANCE);
		map.put("data analyst", DATA_ANALYST);
		map.put("big data", DATA_ANALYST);
		map.put("machine learning", DATA_ANALYST);
		map.put("statistic", DATA_ANALYST);
		map.put("software eng", SOFTWARE_ENGINEERING);
		map.put("developer", SOFTWARE_ENGINEERING);
		map.put("data scientist", DATA_ANALYST);
		
		return map;
		
	}
	
	public static String getLanguagesString(){
		StringBuffer sb = new StringBuffer("(");
		for(String skill : languages){		
			sb.append("|(" + skill + ")");
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
