from selenium import webdriver
from bs4 import BeautifulSoup
import time
import csv

def get_game_links() -> [str]:
    games = []

    for i in range(1):
        driver = webdriver.Chrome()
        driver.get(f"https://steamdb.info/instantsearch/?page={i+1}")
        time.sleep(3)
        page_soure = driver.page_source
        soup = BeautifulSoup(page_soure, "html.parser")

        games += soup.find_all('a', attrs = {'class' : 'app s-hit-list'})

    links = []

    for g in games:
        href = g.get('href')
        links.append(href)
    return links

def lowest_price_games(links) -> [str]:
    list = []
    for l in links:
        driver = webdriver.Chrome()
        driver.get(f'https://steamdb.info{l}')
        time.sleep(5)
        page_soure = driver.page_source
        soup = BeautifulSoup(page_soure, "html.parser")

        current_price = soup.find('td', attrs = {'class' : 'table-prices-converted muted'})
        lowest_price = soup.find('td', attrs = {'data-sort-column-key' : 'b'})

        if current_price != None and lowest_price != None:            
            if current_price == lowest_price:
                name = soup.find('h1', attrs={'itemprop': 'name'})   
                list.append(f'{name.text}: {current_price.text}')
            driver.quit()
        else:
            driver.quit()
            time.sleep(60)
    

if __name__ == "__main__":
    links = get_game_links()
    list = lowest_price_games(links)
    for l in list:
        print(l)