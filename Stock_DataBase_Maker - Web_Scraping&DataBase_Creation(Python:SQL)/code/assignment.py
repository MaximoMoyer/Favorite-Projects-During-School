from bs4 import BeautifulSoup
import requests
import sqlite3
import json

# ### IEX TRADING API METHODS ###
IEX_TRADING_URL = "https://cloud.iexapis.com/stable/stock/"


# ### YAHOO FINANCE SCRAPING
MOST_ACTIVE_STOCKS_URL = "https://cs1951a-s21-brown.github.io/resources/stocks_scraping_2021.html"

### Register at IEX to receive your unique token
TOKEN = 'sk_a43d3690d35b4aeda9b480f400d1934a'

# TODO: Use BeautifulSoup and requests to collect data required for the assignment.
r = requests.get(MOST_ACTIVE_STOCKS_URL)
soup = BeautifulSoup(r.text,'html.parser')
print(soup.prettify)
html_table = soup.find("table")
rows = html_table.find_all('tr')

#removing header
rows = rows[1:]
names = []
symbols = []
price = []
pChange = []
volume = []
HQ = []
for row in rows:
	names.append(row.find('a').string)
	temp = [c.string for c in row.find_all('td')]
	symbols.append(temp[2][:-1])
	price.append(float(temp[3].replace(',','')))
	pChange.append(float(temp[5].replace('%','')))
	volNum = temp[6]
	if 'K' in volNum:
		volume.append(int(float(volNum.replace('K',''))*1000))
	if 'M' in volNum:
		volume.append(int(float(volNum.replace('M',''))*1000000))
	HQ.append(temp[-1].lower().strip())



# # TODO: Save data below.

#TODO: Use IEX trading API to collect sector and news data.
closing = []
delIndex = []
for k in range(len(symbols)):
	url = IEX_TRADING_URL+symbols[k]+ "/chart/5d?chartByDay=true&chartCloseOnly=true&token=" +TOKEN
	response = requests.get(url)
	if response.status_code != 404 and len(response.json()) != 0:
		total = 0
		for i in response.json():
			total += i['close']
		closing.append(total/len(response.json()))
	else:
		delIndex.append(k)
		
for i in range(0,len(delIndex)):
	del symbols[delIndex[i] - i]
	del names[delIndex[i] - i]
	del price[delIndex[i] - i]
	del pChange[delIndex[i] - i]
	del volume[delIndex[i] - i]
	del HQ[delIndex[i] - i]




news = []
for symbol in symbols:
	url = IEX_TRADING_URL+symbol+"/news/?chartByDay=true&chartCloseOnly=true&token="+TOKEN
	response = requests.get(url)
	articles = len(response.json())
	if articles != 0:
		k = 0
		for i in response.json():
			if i['datetime'] > 1612137600000:
				k +=1
		news.append(k)
	else:
		news.append(0)


# Create connection to database
conn = sqlite3.connect('data.db')
c = conn.cursor()

# Delete tables if they exist
c.execute('DROP TABLE IF EXISTS "companies";')
c.execute('DROP TABLE IF EXISTS "quotes";')

#TODO: Create tables in the database and add data to it. REMEMBER TO COMMIT
create_companies_table_command = '''
CREATE TABLE Companies (
	symbol text NOT NULL PRIMARY KEY,
  	name text,
	,
);
'''
c.execute('CREATE TABLE companies(symbol text NOT NULL PRIMARY KEY, name text, location text)')
conn.commit()
c.execute('CREATE TABLE quotes(symbol text not null PRIMARY KEY, price float, avg_price float, num_articles int, volume int, change_pct float)')
conn.commit()

for i in range(len(symbols)):
	c.execute('INSERT INTO companies VALUES (?, ?, ?)', (symbols[i],names[i],HQ[i]))
	c.execute('INSERT INTO quotes VALUES (?, ?, ?, ?, ?, ?)', (symbols[i], price[i], closing[i], news[i],volume[i], pChange[i]))
conn.commit()








