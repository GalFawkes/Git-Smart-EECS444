import listmaker as scrape
import time
import datetime
import sys

if(len(sys.argv) < 2):
    print("Usage: python webscraper.py <number of hours>")
else:
    try:
        time = int(sys.argv[1]) * 4
        print(str(time) + " iterations!")
        for x in range(1, time): #15 min between scan, 25 hours of scanning
            print("Iteration " + str(x) + " at time " +  datetime.datetime.now().strftime("%H:%M:%S"))
            repoList = scrape.getAllValues()
            scrape.exportToCSV("scraperresults.csv", repoList)
            scrape.cleanupCSV("scraperresults.csv")
            time.sleep(900)
    except (TypeError, ValueError) as e:
        print("Please enter an integer")
