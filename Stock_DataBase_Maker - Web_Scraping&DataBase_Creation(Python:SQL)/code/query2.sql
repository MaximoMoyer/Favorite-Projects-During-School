
Select companies.name
From quotes
Left Join companies on quotes.symbol = companies.symbol
WHERE quotes.num_articles < 10
ORDER BY price DESC
LIMIT 1
