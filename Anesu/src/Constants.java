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
	public static Set<String> cities;
	
	static{
		requirementsKeywords = new String[]{"experience", "require"};
		cities = getCities("data/cities.txt");
		languages = new String[]{"java", "javascript", "php", "python", "objective-c"
				, "ruby", "perl", "c", "c\\+\\+", "c#", "swift", "sql", "go", "haskell", "scala", "bash"
				, "lua", "clojure", "assembly", "html", "css"};
		positions = new String[]{"customer support agent", "android angineer"};
		languagesSet = new HashSet<>(Arrays.asList(languages));
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
