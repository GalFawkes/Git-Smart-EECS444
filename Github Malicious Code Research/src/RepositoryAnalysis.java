import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RepositoryAnalysis {
	public static double ALLOWED_CALLS_PER_DAY = 19000;
	public static double SECONDS_PER_DAY = 86400; 
	
	public static String resultsDirectory = "\\results\\";
	public static String header = "Project_id,Project name,Project link,VT link,VT detection result,Propogation link,Propogation method\n";
	
	public static void main(String[] args){
		File results = nextResultFile();
		writeToFile(results, header);
		
		long startTime = System.currentTimeMillis();
		long calls = 0;
		int resultsPrinted = 0;
		ArrayList<VirusChecker> checks = new ArrayList<VirusChecker>();
		
		String[] repositories = {
				"https://github.com/CSho27/SoftwareSecurity_HW2",
				"https://github.com/mikesiko/PracticalMalwareAnalysis-Labs",
				"https://github.com/brunosimon/folio-2019",
				"https://github.com/neex/phuip-fpizdam",
				"https://github.com/polynote/polynote",
				"https://github.com/muhammederdem/vue-interactive-paycard",
				"https://github.com/microsoft/PowerToys",
				"https://github.com/shadowsocks/shadowsocks-android",
				"https://github.com/iptv-org/iptv",
				"https://github.com/evilsocket/pwnagotchi",
				"https://github.com/vlang/v",
				"https://github.com/geekcomputers/Python",
				"https://github.com/TheAlgorithms/Java",
				"https://github.com/robbyrussell/oh-my-zsh",
				"https://github.com/home-assistant/home-assistant",
				"https://github.com/axi0mX/ipwndfu",
				"https://github.com/mitesh77/Best-Flutter-UI-Templates",
				"https://github.com/storybookjs/storybook",
				"https://github.com/nvbn/thefuck",
				"https://github.com/elunez/eladmin",
				"https://github.com/2dust/v2rayN",
				"https://github.com/pytorch/pytorch",
				"https://github.com/llvm/llvm-project",
				"https://github.com/sinclairzx81/zero",
				"https://github.com/hoanhan101/ultimate-go",
				"https://github.com/hsoft/collapseos",
				"https://github.com/kmario23/deep-learning-drizzle",
				"https://github.com/o2sh/onefetch",
				"https://github.com/deepstreamIO/deepstream.io",
				"https://github.com/gin-gonic/gin",
				"https://github.com/ruanyf/free-books",
				"https://github.com/google-research/google-research",
				"https://github.com/davidkpiano/xstate",
				"https://github.com/bitcoin/bitcoin",
				"https://github.com/geekcomputers/Python",
				"https://github.com/pubkey/rxdb",
				"https://github.com/bitcoinbook/bitcoinbook",
				"https://github.com/GoAdminGroup/go-admin",
				"https://github.com/aholachek/mobile-first-animation",
				"https://github.com/Tikam02/DevOps-Guide",
				"https://github.com/google/eng-practices",
				"https://github.com/521xueweihan/HelloGitHub",
				"https://github.com/GuoZhaoran/spikeSystem",
				"https://github.com/frank-lam/fullstack-tutorial",
				"https://github.com/google-research/bert",
				"https://github.com/godotengine/godot"
		};
		
		int scannedRepositories=0;
		while(resultsPrinted < checks.size() || scannedRepositories<repositories.length){
			//Scan any unscanned repositories
			if(scannedRepositories<repositories.length){
				String repo = repositories[scannedRepositories];
				System.out.println("downloading... "+repo);
				RepositoryHandler rph = new RepositoryHandler(repo);
				flowControl(calls, startTime);
				System.out.println("scanning...    "+repo);
				VirusChecker vc = new VirusChecker(scannedRepositories, repo, rph.repository);
				if(rph.repository != null){	
					vc.requestScan();
				}
				checks.add(vc);
				calls += vc.calls;
				rph.close();
				scannedRepositories++;
			}
			
			//handle any unretrieved results
			for(int i=resultsPrinted; i<checks.size(); i++){
				if(checks.get(i).getWaitTime()<0){
					calls++;
					flowControl(calls, startTime);
					System.out.println("retrieving...  "+checks.get(i).url);
					VirusCheck response = checks.get(i).retrieveResponse();
					writeToFile(results, response.toCSV());
					resultsPrinted++;
				}
			}
		}
		
		double timeSinceStart = secondsSinceStart(startTime);
		System.out.println("Calls: "+calls);
		System.out.println("Time: "+timeSinceStart);
		System.out.println("Rate (calls/day): "+(calls*SECONDS_PER_DAY)/timeSinceStart);
		System.out.println("Rate (calls/month): "+(calls*SECONDS_PER_DAY*30)/timeSinceStart);
		
	}
	
	public static double secondsSinceStart(long startTime){
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}
	
	public static void writeToFile(File file, String string){
		BufferedWriter writer = null;
		try {
			FileWriter fwriter = new FileWriter(file, true);
			writer = new BufferedWriter(fwriter);
			writer.write(string);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static File nextResultFile(){
		File resultsFolder = new File(System.getProperty("user.dir")+resultsDirectory);
		File[] resultFiles = resultsFolder.listFiles();
		
		if(resultFiles.length > 0){
			File lastRun = resultFiles[resultFiles.length-1];
			String[] spaceSplit = lastRun.getName().split(" ");
			if(spaceSplit.length>0){
				String[] dotSplit = spaceSplit[1].split("\\.");
				if(dotSplit.length > 0){
					int trialNum = Integer.parseInt(dotSplit[0]);
					return new File(resultsFolder.getAbsolutePath()+"\\Trial "+(trialNum+1)+".csv");
				}
			}
			System.out.println("ERROR: invalid file included");
			return null;
		} else {
			return new File(resultsFolder.getAbsolutePath()+"\\Trial 1.csv");
		}
	}
	
	
	public static String resultsToString(String repository, VirusCheck result){
		String string = "";
		string += "-"+result.getRepo()+": "+result.getStatus()+" ["+result.response.positives+"/"+result.response.total+"]\n";
		return string;
	}
	
	public static void flowControl(long calls, long startTime){
		Double callsPerDay = (calls*SECONDS_PER_DAY)/secondsSinceStart(startTime);
		if(callsPerDay > ALLOWED_CALLS_PER_DAY){
			double allowedCallsPerSecond = ALLOWED_CALLS_PER_DAY / SECONDS_PER_DAY;
			double waitTime = (calls/allowedCallsPerSecond)-secondsSinceStart(startTime);
			System.out.println("calls/day:"+callsPerDay);
			System.out.println("wait time: "+waitTime);
			try {
				Thread.sleep((long) Math.ceil(waitTime*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
