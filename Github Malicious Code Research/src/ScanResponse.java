
public class ScanResponse {
	String scan_id;
	String sha1;
	String resource;
	int reponse_code;
	String sha256;
	String permalink;
	String md5;
	String verbose_msg;
	
	int calls = 0;
	
	public static ScanResponse nullScanResponse(int calls){
		ScanResponse sc = new ScanResponse();
		sc.calls = calls;
		return sc;
	}
}
