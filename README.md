# compusec-f2019-project
This is a project for EECS 349/444 at CWRU in Fall 2019.
Group Members: Steven Nyeo (EECS 444), Patrick Hogrell (EECS 349), Zubair Mukhi (EECS 349), and Chris Shorter (EECS 349)

## Repo Analysis Tool
How to use GitHub repo analysis tool with Java:
1. Navigate to Github_Malicious_Code_Research folder
2. Run GitHub_VT_Repository_Analysis_Tool.jar with the filepath of your list of repositories as the first arguments
ex. java -jar GitHub_VT_Repository_Analysis_Tool.jar "C:\Users\short\compusec-f2019-project\Webscraper\scraperresults.csv"
3. The tool will tell you where it is writing the results, but basically it's gonna create a new file in the results folder to write its results to:
***Github Repository Analysis***

Analyzing 964 repos in C:\Users\short\compusec-f2019-project\Webscraper\scraperresults.csv
Writing results to C:\Users\short\compusec-f2019-project\Github_Malicious_Code_Research\results\Trial_2.csv


## Webscraper
### Installation
`cd Webscraper`
`pip install -r requirements.txt`
### Execution
python webscraper.py <ttl> "<Starting URL>"
### Other notes
This starts on a repo and spirals out from there. It takes a long time if you set the TTL too high, but then again, so do all the best things, right?
