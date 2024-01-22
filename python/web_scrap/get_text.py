from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
from bs4 import BeautifulSoup

url = "https://aquinas.cc/la/en/~Rom"

driver = webdriver.Chrome()
driver.get(url)
time.sleep(1)

paragraph = 0
text = ''

for i in range(210):
    time.sleep(1)
    #load page
    page_source = driver.page_source
    soup = BeautifulSoup(page_source, 'html.parser')
    #get next 20 paragraphs
    for a in range(20):
        if paragraph > 4198:
            print("Not Found")
        else:
            print(paragraph)
            english = soup.find('vl-c', id=f"c2_{paragraph}")
            text += english.text + '\n'
            paragraph+=1
    #scrolls down to appear new paragraphs
    driver.find_element("tag name", "body").send_keys(Keys.END)
with open("text", "w") as output:
    output.write(text)