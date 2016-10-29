import java.net.URISyntaxException;

import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class InternshipLink {
	private String company;
	private String link;
	private String companySite;
	
	public InternshipLink(String company, String link){
		this.company = company.trim();
		this.link = link.trim();
		
		try {
			companySite = getDomainName(link);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	
}
