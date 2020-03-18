# Git Smart, Scan Before Use
This is a project for EECS 349/444 at CWRU in Fall 2019.
Group Members: Steven Nyeo (EECS 444), Patrick Hogrell (EECS 349), Zubair Mukhi (EECS 349), and Chris Shorter (EECS 349)

## Repo Analysis Tool
_please note that the API keys associated with VirusTotal in the .jar file are being revoked. You will need to rebuild the application with your own API keys to correctly use the scanner._
How to use GitHub repo analysis tool with Java:
1. Navigate to Github_Repo_Analysis_Tool folder
2. Run GitHub_VT_Repository_Analysis_Tool.jar with either:
-the -s flag and the url of a single repository
ex. java -jar GitHub_VT_Repository_Analysis_Tool.jar -a "https://github.com/AnonMuk/Git-Smart-EECS444/tree/Analized-Results/Github_Malicious_Code_Research/results"
-the -a flag and the filepath of your list of repositories as the first arguments
ex. java -jar GitHub_VT_Repository_Analysis_Tool.jar -a "C:\Users\short\compusec-f2019-project\Webscraper\scraperresults.csv"
3. The tool will tell you where it is writing the results, but basically it's gonna create a new file in the results folder to write its results to:
***Github Repository Analysis***

Analyzing 964 repos in C:\Users\short\compusec-f2019-project\Webscraper\scraperresults.csv
Writing results to C:\Users\short\compusec-f2019-project\Github_Malicious_Code_Research\results\Trial_2.csv


## Webscraper
This webscraper is a recursive tree search that starts on a given repository URL, compiles a list of repositories belonging to the user, finds all their followers, and collects all their repositories as well. It outputs to the end of `scraperresults.csv` and removes duplicate repositories by full GitHub URL, not by project name. Only run at TTL <=2 unless you have multiple hours to spare on the device running the scraper.
TTL = 3 takes multiple hours and generated 86,000 entries
### Installation
`cd Git-Smart-EECS444/Webscraper`

`pip install -r requirements.txt`
### Execution
`python webscraper.py [ttl] [Starting URL]`


## Report
The report is available as both <a href="https://github.com/AnonMuk/Git-Smart-EECS444/blob/master/Report/project.pdf">project.pdf</a> and <a href="https://github.com/AnonMuk/Git-Smart-EECS444/blob/master/Report/Git%20Smart.pdf">Git Smart.pdf</a>.
