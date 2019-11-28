import requests
import re
from bs4 import BeautifulSoup  # BEAUTIFULSOUP


def spiralOut(url, ttl):
    print("TTL:", ttl, "| URL: " + url)
    projectList = []
    # Step 1: Get user URL from GitHub URL
    reg = re.compile("https://github.com/[\w\-\.]+")
    reg2 = re.compile("https://github.com/")
    reg3 = re.compile("/users/[\w\-\.]+")
    reg4 = re.compile("/users")
    # Step 1.5: Match the user URL, hand to string
    userURL = reg.match(url).group()
    uName = reg2.sub("", userURL)
    fTab = userURL + "?tab=followers"
    response = requests.get(fTab)
    soup = BeautifulSoup(response.text, "html.parser")
    followerList = []
    for i in range(36, len(soup.findAll('a'))):
        one_a_tag = soup.findAll('a')[i]
        target = one_a_tag.get('data-hovercard-url')
        # print(target)
        if target is not None:
            rm3 = reg3.match(target)
            if rm3 is not None:
                rm4 = reg4.sub("", rm3.group())
                followerList.append("https://github.com" + rm4)
    # Step 2: Find all of the user's repos, add to list.
    followerList = list(dict.fromkeys(followerList))
    rTab = userURL + "?tab=repositories"
    pl = getRepos(rTab, uName)
    for project in pl:
        projectList.append(project)
    # Step 2.5: Terminate on base case.
    if(ttl <= 0):
        print("TTL:", ttl, "| Username: " + uName)
        return projectList
    # Step 3: Find Followers, call spiralOut on each one, handing URL and ttl.
    for follower in followerList:
        # print("TTL:", ttl)
        # print(follower)
        pl = spiralOut(follower, ttl - 1)
        for project in pl:
            projectList.append(project)
    projectList = list(dict.fromkeys(projectList))
    return projectList


def getRepos(url, uName):
    print("URL: " + url + ", Username: " + uName)
    response = requests.get(url)
    reg = re.compile("/" + uName + "/[\w\-\.]+")
    projectList = []
    soup = BeautifulSoup(response.text, "html.parser")
    for i in range(36, len(soup.findAll('a'))):
        one_a_tag = soup.findAll('a')[i]
        target = one_a_tag.get('href')
        if reg.match(target) is not None:
            if not ("topics/" in reg.match(target).group()):
                if not ("security/" in reg.match(target).group()):
                    if not ("about/" in reg.match(target).group()):
                        if not ("github/" in reg.match(target).group()):
                            projectList.append("https://github.com" + reg.match(target).group())
    return projectList
