import listmaker as scrape
import time
import datetime

for x in range(0,20):
    print("Iteration " + datetime.datetime.now().strftime("%H:%M:%S"))
    repoList = scrape.getAllValues()
    scrape.exportToCSV("scraperresults.csv", repoList)
    scrape.cleanupCSV("scraperresults.csv")
    time.sleep(900)
