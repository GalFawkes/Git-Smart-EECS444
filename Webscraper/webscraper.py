import listmaker as scrape

url = 'https://github.com/topics/cpp'
# use a list of URLs and iterate through them for popular topics scraping
# e.g. urlList = ["1", "2", "3"]
# for url in urlList:
#   repoList = scrape.getProjectList(url)
#   scrape.exportToCSV("scraperresults.csv", repoList)

#EXPORTTOCSV DOES NOT CHECK FOR DUPLICATES

repoList = scrape.getAllValues()
scrape.exportToCSV("scraperresults.csv", repoList)
scrape.cleanupCSV("scraperresults.csv")
