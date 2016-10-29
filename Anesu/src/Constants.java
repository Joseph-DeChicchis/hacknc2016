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
	public static String[] languages = {"java", "javascript", "php", "python", "objective-c"
			, "ruby", "perl", "c", "c++", "c#", "swift", "sql", "go", "haskell", "scala", "bash"
			, "lua", "clojure", "assembly", "html", "css"};
	
	public static Set<String> languagesSet = new HashSet<>(Arrays.asList(languages));
	public static String[] positions = {"customer support agent", "android angineer"};
	public static Set<String> cities;
	
	static{
		cities = getCities("data/cities.txt");
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
