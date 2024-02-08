from selenium import webdriver
from bs4 import BeautifulSoup
import time

class Date:
    def __init__(self, month, day):
        self.month = month
        self.day = day

def clean_temperature_string(temp_str: str) -> float: 
    return float(''.join(char for char in temp_str if char.isdigit() or char == '.'))

def fahrenheit_to_celsius(f) -> float:
    f = clean_temperature_string(f)
    return round((f - 32) / 1.8)


url = "https://www.wunderground.com/forecast/br/porto-alegre"

driver = webdriver.Chrome()
driver.get(url)
time.sleep(3)
page_source = driver.page_source
driver.quit()

soup = BeautifulSoup(page_source, 'html.parser')
days = soup.select('div.obs-date')
temp_high = soup.select('span.temp-hi')
temp_low = soup.select('span.temp-lo')

# for i in range(len(days)):
#     high = fahrenheit_to_celsius(temp_high[i].text)
#     low = fahrenheit_to_celsius(temp_low[i].text)
#     print(f'{days[i].text}: {high}°C/{low}°C')

dates = []
for d in days:
    aux = d.text.split(' ')[1].split('/')
    date = Date(month=aux[0], day=aux[1])
    dates.append(date)
for date in dates:
    url2 = f"https://www.wunderground.com/hourly/br/porto-alegre/date/2024-{date.month}-{date.day}"
    driver = webdriver.Chrome()
    driver.get(url2)
    time.sleep(3)
    page_source2 = driver.page_source
    driver.quit()
    soup = BeautifulSoup(page_source2, 'html.parser')
    hours = soup.select('td.mat-cell.cdk-cell.cdk-column-timeHour.mat-column-timeHour.ng-star-inserted')
    temperature = soup.select('td.mat-cell.cdk-cell.cdk-column-temperature.mat-column-temperature.ng-star-inserted')
    rain_percent = soup.select('td.mat-cell.cdk-cell.cdk-column-precipitation.mat-column-precipitation.ng-star-inserted')
    for i in range(len(hours)):
        print(f'{hours[i].text}: {fahrenheit_to_celsius(temperature[i].text)}°C / {rain_percent[i].text}')
