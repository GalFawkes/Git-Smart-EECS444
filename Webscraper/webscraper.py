import listmaker as scrape
import scraper2
import sys
from datetime import datetime

if(len(sys.argv) < 2):
    print("Usage: python webscraper.py <TTL> <Starting URL>")
else:
    try:
        startTime = datetime.now()
        ttl = int(sys.argv[1])
        url = sys.argv[2]
        repoList = scraper2.spiralOut(url, ttl)
        scrape.exportToCSV("scraperresults.csv", repoList)
        scrape.cleanupCSV("scraperresults.csv")
        endTime = datetime.now()
        print("Duration: {} with {} levels of recursion.".format((endTime - startTime),ttl))
    except (TypeError, ValueError):
        print("Usage: python webscraper.py <TTL> <Starting URL>")
