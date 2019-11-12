import listmaker as scrape
import time
import datetime

for x in range(0, 100): #15 min between scan, 375 hours of scanning or roughly 15 days
    print("Iteration " + datetime.datetime.now().strftime("%H:%M:%S"))
    repoList = scrape.getAllValues()
    scrape.exportToCSV("scraperresults.csv", repoList)
    scrape.cleanupCSV("scraperresults.csv")
    time.sleep(900)
