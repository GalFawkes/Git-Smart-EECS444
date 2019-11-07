import requests
import urllib.request
import time
import re
from bs4 import BeautifulSoup

# Set the URL you want to webscrape from
url = 'https://github.com/topics/cpp'

# Connect to the URL
response = requests.get(url)

# Parse HTML and save to BeautifulSoup object¶
soup = BeautifulSoup(response.text, "html.parser")

projectList = []
reg = re.compile("/\w+/\w+")
# To download the whole data set, let's do a for loop through all a tags
for i in range(36,len(soup.findAll('a'))): #'a' tags are for links
    one_a_tag = soup.findAll('a')[i]
    target = one_a_tag.get('href')
    rmt = reg.match(target)
    if rmt is not None:
        if not "topics" in rmt.group():
            if not "site" in rmt.group():
                if not "about" in rmt.group():
                    print(rmt.group())
                    projectList.append(rmt.group())

for i in projectList:
    print(i)
#   print(one_a_tag.get('href'))
#    link = one_a_tag['href']
#    print(link)
#    download_url = 'https://github'+ link
#    print(download_url)
#    urllib.request.urlretrieve(download_url,'./'+link)
#    time.sleep(1) #pause the code for a sec

