import requests
from bs4 import BeautifulSoup

url = "https://penelope.uchicago.edu/Thayer/e/roman/texts/plutarch/lives/home.html"
r = requests.get(url)
soup = BeautifulSoup(r.text, "html.parser")

links = soup.find_all('a', attrs={'target': 'Plutarch_E'})

for link in links:
    href = link.get('href').strip()# remove blank lines
    
    r = requests.get('https://penelope.uchicago.edu/Thayer/' + href)
    soup = BeautifulSoup(r.text, "html.parser")
    title_html = soup.find('h1')
    title = title_html.text.strip().split('\n')[1]
    print(title)
    
    paragraphs = soup.find('p', attrs={'class': 'justify'})# for some reason it returns al the paragraphs
    text = paragraphs.text.replace('Â','').replace('â','').replace('Ã','').replace('ã','')
  
    with open(title + '.txt', 'w', encoding='utf-8') as file:
        file.write(text)
    