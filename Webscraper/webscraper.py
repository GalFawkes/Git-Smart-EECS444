import listmaker as scrape
import time

url = 'https://github.com/topics/cpp'
# use a list of URLs and iterate through them for popular topics scraping
# e.g. urlList = ["1", "2", "3"]
# for url in urlList:
#   repoList = scrape.getProjectList(url)
#   scrape.exportToCSV("scraperresults.csv", repoList)

#EXPORTTOCSV DOES NOT CHECK FOR DUPLICATES

for x in xrange (0,20):
    repoList = scrape.getAllValues()
    scrape.exportToCSV("scraperresults.csv", repoList)
    scrape.cleanupCSV("scraperresults.csv")
    time.sleep(900)
