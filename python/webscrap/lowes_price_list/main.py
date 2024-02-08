import requests
from bs4 import BeautifulSoup
import csv
import os

try:
    os.remove("games.csv")
except:
    print("File not found")

r = requests.get('https://gg.deals/deals/?minRating=0&onlyHistoricalLow=1&store=4&type=1&page=1')
soup = BeautifulSoup(r.text, 'html.parser')
number_of_pages = int(soup.find('a', attrs={'aria-label': 'Last page'}).text.strip('...'))

for i in range(number_of_pages):
    r = requests.get(f'https://gg.deals/deals/?minRating=0&onlyHistoricalLow=1&store=4&type=1&page={i+1}')
    soup = BeautifulSoup(r.text, 'html.parser')
    names = soup.find_all('a', attrs={'class': 'game-info-title title'})
    prices = soup.find_all('span', attrs={'class': 'price-inner game-price-new'})
    with open("games.csv", encoding="utf-8", mode="a", newline='') as csvfile:
        fieldnames = ['name', 'price']
        writer = csv.DictWriter(csvfile, fieldnames)
        for j in range(len(names)):
            writer.writerow({'name': names[j].text, 'price': prices[j].text})
    print(f'{i+1}/{number_of_pages}')