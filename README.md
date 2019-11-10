# compusec-f2019-project
This is a project for EECS 349/444 at CWRU in Fall 2019.
Group Members: Steven Nyeo (EECS 444), Patrick Hogrell (EECS 349), Zubair Mukhi (EECS 349), and Chris Shorter (EECS 349)

## Repo Analysis Tool
How to use GitHub repo analysis tool with Java:
1. I used Eclipse to write this, but it really doesn't matter as long as you can build the project
2. There's a class called `RepositoryAnalysis.java`. In it is a method called `analyzeRepos()` that takes a csv of github repositories and tests each one, writing the results to a file in the results folder.
3. To use this,


## Webscraper
### Installation
`cd Webscraper`
`pip install -r requirements.txt`
### Other notes
Currently, this scans the topics page of GitHub and pulls any new repositories into `scraperresults.csv`. It's configured to run every 15 mins for five hours via the loops in webscraper.py. Stretch goals are to allow for interactive time-setting via the application or as arguments on the command line
