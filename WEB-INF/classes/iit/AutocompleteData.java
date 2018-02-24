package iit;

public class AutocompleteData {

	private String value;
	private String url;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public AutocompleteData(String value, String url) {
		super();
		this.value = value;
		this.url = url;
	}
	
	
}
