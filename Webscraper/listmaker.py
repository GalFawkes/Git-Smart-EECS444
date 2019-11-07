import requests
import urllib.request
import urllib.parse
import time
import re
from bs4 import BeautifulSoup

def getProjectList(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    projectList = []
    reg = re.compile("/\w+/\w+")
    # To download the whole data set, let's do a for loop through all a tags
    makeProjectList(reg, projectList, soup)
    projectList = list(dict.fromkeys(projectList))
    return projectList


def makeProjectList(reg, projectList, soup):
    for i in range(36,len(soup.findAll('a'))): #'a' tags are for links
        one_a_tag = soup.findAll('a')[i]
        target = one_a_tag.get('href')
        rmt = reg.match(target)
        if rmt is not None:
            if not "topics" in rmt.group():
                if not "site" in rmt.group():
                    if not "about" in rmt.group():
                        projectList.append(rmt.group())

def exportToCSV(path, projectList):
    f = open(path, "a")
    for link in projectList:
        f.write("https://github.com"+link+"\n")
    f.close()
