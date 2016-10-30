import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import com.jaunt.Element;
import com.jaunt.UserAgent;
import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class InternshipLink {
	private String company;
	private String link;
	private String companySite;
	
	private Set<String> location, positions, langauges;
	private String size, platform;

	public InternshipLink(String company, String link){
		this.company = company.trim();
		this.link = link.trim();

	}
	
	private static String getDomainName(String url) throws URISyntaxException {
	    URI uri = null;
		try {
			uri = new URI(url);
		} catch (MalformedURIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	public String toString(){
		String str = String.format("%s, %s: %s", company, companySite, link);
		return str;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
	}
	
	public Set<String> getLocation() {
		return location;
	}

	public void setLocation(Set<String> location) {
		this.location = location;
	}

	public Set<String> getPositions() {
		return positions;
	}

	public void setPositions(Set<String> positions) {
		this.positions = positions;
	}

	public Set<String> getLangauges() {
		return langauges;
	}

	public void setLangauges(Set<String> langauges) {
		this.langauges = langauges;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
