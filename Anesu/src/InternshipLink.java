
public class InternshipLink {
	private String company;
	private String link;
	
	public InternshipLink(String company, String link){
		this.company = company.trim();
		this.link = link.trim();
	}
	
	public String toString(){
		String str = String.format("%s: %s", company, link);
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
	
	
}
