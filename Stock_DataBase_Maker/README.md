# Summary #
Simple project that scrapes Yahoo Finance and pulls data from the IEX trading API to get information on various stocks. The assignment.py script scrapes Yahoo Finance to get the names, stocks symbols, price, %daily, volume traded, and HQ location of various stocks. It also uses the IEXtrading API endpoint to get information to get the average closing price of each of the most active stocks over the last 5 days, and the number of articles recently written about each stock. It then saves this data down into the data.db database. The SQL files are various queries written to pull diffent cuts of this data.

## Note ##
This script may be difficult to run for someone new using it The Yahoo Finance URL used will likely be outdated, so the HTML will have changed. Moreover, one would need to generate their own Token to be able to query IEX.
