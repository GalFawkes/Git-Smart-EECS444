import listmaker as scrape

# Set the URL you want to webscrape from
url = 'https://github.com/topics/cpp'
#use a list of URLs and iterate through them for popular topics scraping

repoList = scrape.getProjectList(url)
scrape.exportToCSV("scraperresults.csv", repoList)
