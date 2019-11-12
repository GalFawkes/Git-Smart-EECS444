import requests
import urllib.request
import urllib.parse
import time
import re
from bs4 import BeautifulSoup

def getAllValues():
    tList = getTopics()
    topicsList = []
    for url in tList:
        print(url)
        plist = getProjectList(url)
        for p in plist:
            topicsList.append(p)
    return topicsList


def getProjectList(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    projectList = []
    reg = re.compile("/[\w\-]+/[\w\-]+")
    # To download the whole data set, let's do a for loop through all a tags
    makeProjectList(reg, projectList, soup)
    projectList = list(dict.fromkeys(projectList))
    return projectList


def makeProjectList(reg, projectList, soup):
    for i in range(36,len(soup.findAll('a'))): #'a' tags are for links
        one_a_tag = soup.findAll('a')[i]
        target = one_a_tag.get('href')
        rmt = regexTest(target)
        if rmt is not None:
            if not "topics" in rmt.group():
                if not "site" in rmt.group():
                    if not "about" in rmt.group():
                        projectList.append("https://github.com" + rmt.group())

def getTopics():
    response = requests.get("https://github.com/topics/")
    soup = BeautifulSoup(response.text, "html.parser")
    topicList = []
    reg = re.compile("/topics/[\w\-\.]+")
    for i in range(36, len(soup.findAll('a'))):
        one_a_tag = soup.findAll('a')[i]
        target = one_a_tag.get('href')
        rmt = reg.match(target)
        if rmt is not None:
            if not "site" in rmt.group():
                if not "about" in rmt.group():
                    topicList.append("https://github.com" + rmt.group())
    topicList.append("https://github.com/topics/poc")
    topicList.append("https://github.com/topics/exploit")
    topicList.append("https://github.com/topics/vulnerability")
    topicList = list(dict.fromkeys(topicList))
    return topicList

def exportToCSV(path, projectList):
    f = open(path, "r")
    line = f.readline()
    currentList = []
    while line:
        currentList.append(line)
        line = f.readline()
    f.close()
    f = open(path, "a+")
    for url in line:
        projectList.append(url)
    for link in projectList:
        f.write(link+"\n")
    f.close()


def cleanupCSV(path):
    topicsList = []
    f = open(path, "r")
    line = f.readline()
    while line:
        topicsList.append(line)
        line = f.readline()
    topicsList = list(dict.fromkeys(topicsList))
    f.close()
    f = open(path, "w")
    for url in topicsList:
        f.write(url)
    f.close()

def regexTest(target):
    reg = re.compile("/[\w\-\.]+/[\w\-\.]+")
    reg2 = re.compile("https://github.com")
    target = reg2.sub("", target)
    return reg.match(target)
