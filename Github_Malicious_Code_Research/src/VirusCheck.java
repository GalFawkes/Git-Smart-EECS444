import java.util.ArrayList;

public class VirusCheck {
	public static String com = ",";
	
	String url;
	ReportResponse response;
	int id = 0;
	
	//To be instantiated on return
	public VirusCheck(int id, String url, ReportResponse response){
		this.url = url;
		this.id = id;
		this.response = response;
	}
	
	public boolean passedCheck(){
		if(response == null){
			return true;
		}else if((double) response.positives / response.total > 0.25){
			return false;
		} else {
			return true;
		}
	}
	
	public String toString(){
		return  "-"+getRepo()+": "+getStatus()+" ["+response.positives+"/"+response.total+"]";
	}
	
	public String toCSV(){
		if(response != null){
			return id+com+
					getRepo()+com+
					url+com+
					getGuiLink()+com+
					"["+response.positives+"/"+response.total+"]"+com+
					"\n";
		} else {
			return id+com+
					getRepo()+com+
					url+com+
					"N/A"+com+
					"Download Error"+com+
					"\n";
		}
	}
	
	public String getStatus(){
		return passedCheck() ? "Pass" : "Fail";
	}
	
	public String getRepo(){
		String[] components = url.split("/");
		if(components.length>0){
			return components[components.length-1];
		} else {
			return "";
		}
		
	}
	
	public String getGuiLink(){
		ArrayList<String> components = new ArrayList<String>();
		if(response.permalink != null){
			String[] parts = response.permalink.split("/");
			for(int i=0; i<parts.length; i++){
				components.add(parts[i]);
			}
			components.add(3, "gui");
			components.remove(components.size()-1);
			components.remove(components.size()-1);
			components.add("detection");
			String url = "";
			for(String component : components){
				url += component+"/";
			}
			return url;
		} else {
			return "N/A";
		}
	}
}
