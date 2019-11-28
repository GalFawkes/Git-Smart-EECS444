import re


def exportToCSV(path, projectList):
    f = open(path, "a+")
    list = removeUsers(projectList)
    for link in list:
        f.write(link + "\n")
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
        print(url)
        f.write(url)
    f.close()


def removeUsers(projectList):
    pList2 = []
    reg = re.compile("https://github.com/user/")
    for project in projectList:
        if reg.match(project) is None:
            pList2.append(project)
    return pList2
