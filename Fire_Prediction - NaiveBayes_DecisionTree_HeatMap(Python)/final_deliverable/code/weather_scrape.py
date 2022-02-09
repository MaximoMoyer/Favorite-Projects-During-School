import requests
import csv
import pandas as pd

data_path = "C:/Users/Kolya/Documents/Fire/nfirs_data/2018/NFIRS_FIRES_2018_110119/"
folder_path = "C:/Users/Kolya/Documents/Fire/nfirs_data/batch_files/"
fs = pd.read_csv(data_path + "fire_mash.csv")
#fs = fs.iloc[600:]
weather_cols = ['Name', 'Date time', 'Maximum Temperature', 'Minimum Temperature', 'Temperature', 'Wind Chill', 'Heat Index', 'Precipitation', 'Snow Depth', 'Wind Speed', 'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type', 'Conditions']
lwc = len(weather_cols)
w_df = pd.DataFrame(index=fs.index, columns=list(fs.columns)+ weather_cols)
w_df[list(fs.columns)] = fs
batch_size = 100

TOKEN = "AN3R9EG4PAXCGKRCY7GBW4AY9"

responses = []
cur_df = w_df.iloc[:batch_size]
for i, row in fs.iterrows():
	zipCode = row["ZIP5"]
	date = row["yyyy-mm-dd"]
	url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/history?aggregateHours=24&combinationMethod=aggregate&startDateTime=" + date + "T00%3A00%3A00&endDateTime=" + date + "T00%3A00%3A00&maxStations=-1&maxDistance=-1&contentType=csv&unitGroup=us&locationMode=single&key=" + TOKEN + "&dataElements=default&locations=" + str(zipCode)
	r = requests.get(url)
	responses.append(r.content.decode('utf-8'))
	cr = csv.reader(r.content.decode('utf-8').splitlines(), delimiter=',')
	my_list = list(cr)
	#w_df.iloc[i, -lwc:] = my_list[1]
	cur_df.iloc[i, -lwc:] = my_list[1]
	if ((i%batch_size) == 0) and (i > 0):
		#w_df.iloc[i-batch_size:i].to_csv(folder_path + "2018_batch_" + str(i) + ".csv")
		cur_df.to_csv(folder_path + "2018_batch_" + str(i) + ".csv")
		cur_df = w_df.iloc[i:i+batch_size]
		print(i)
cur_df.iloc[i//batch_size:i].to_csv(folder_path + "2018_batch_" + str(i) + ".csv")
#print(w_df)
#w_df.to_csv(data_path + "weather_df.csv")
