import requests
from bs4 import BeautifulSoup

url = "https://penelope.uchicago.edu/Thayer/e/roman/texts/plutarch/lives/home.html"
r = requests.get(url)
soup = BeautifulSoup(r.text, "html.parser")

links = soup.find_all('a', attrs={'target': 'Plutarch_E'})
hrefs = []
for link in links:
    h = link.get('href')
    hrefs.append(h.strip())# remove blank lines

for i in range(len(hrefs)):
    r = requests.get('https://penelope.uchicago.edu/Thayer/' + hrefs[i])
    soup = BeautifulSoup(r.text, "html.parser")
    title_html = soup.find('h1')
    if title_html != None:
        title = title_html.text.strip().split('\n')[1]
        print(title)
        paragraphs = soup.find_all('p')
        text = ''
        for p in paragraphs:
            aux = p.text.replace('Â','').replace('â','').replace('Ã','').replace('ã','')
            if not text.__contains__(aux):
                text += aux
    with open(title + '.txt', 'w', encoding='utf-8') as file:
        file.write(text)
