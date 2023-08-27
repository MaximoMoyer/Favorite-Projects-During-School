
Select quotes.symbol, companies.name
From quotes
Left Join companies on quotes.symbol = companies.symbol
WHERE quotes.price >35 AND ABS(quotes.price  - quotes.avg_price) <5
ORDER BY ABS(quotes.price  - quotes.avg_price) ASC
