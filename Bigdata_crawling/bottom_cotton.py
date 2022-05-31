import urllib.request
import urllib.parse
from bs4 import BeautifulSoup
from selenium import webdriver
import requests

driver = webdriver.Chrome('C:/Temp/chromedriver')

n = 1
for i in range(1,101):
    url2 = 'https://www.musinsa.com/ranking/best?period=now&age=ALL&mainCategory=003&subCategory=003007&leafCategory=&price=&golf=false&kids=false&newProduct=false&exclusive=false&discount=false&soldOut=false&page='+str(i)+'&viewType=small&priceMin=&priceMax='
    driver.get(url2)
    slacks_list = driver.find_element_by_css_selector('#goodsRankList > li > div > div > a > img')
    req = requests.get(url2)
    html = req.content
    html = html.decode('utf-8')
    bs = BeautifulSoup(html, 'html.parser')
    img = bs.select('#goodsRankList > li > div > div > a > img')
    print(n)
    for content in img:
        imgUrl = content["data-original"]
        with urllib.request.urlopen(imgUrl) as f:
            with open('./images/bottom_cotton/cotton' + str(n) + '.jpg', 'wb') as h:  # w - write b - binary
                img = f.read()
                h.write(img)
            n += 1