
Select quotes.symbol, companies.name
From quotes
Left Join companies on quotes.symbol = companies.symbol
ORDER BY price/avg_price DESC
LIMIT 1
