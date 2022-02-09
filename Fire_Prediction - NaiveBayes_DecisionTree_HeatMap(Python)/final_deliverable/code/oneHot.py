import pandas as pd

def oneHotColumns(df):

	weather_types = ['Blowing Or Drifting Snow', 'Diamond Dust', 'Drizzle', 'Duststorm', 'Fog', 'Freezing Drizzle/Freezing Rain', 'Freezing Fog', 'Funnel Cloud/Tornado', 'Hail', 'Hail Showers', 'Heavy Drizzle', 'Heavy Drizzle/Rain', 'Heavy Freezing Rain', 'Heavy Rain', 'Heavy Rain And Snow', 'Heavy Snow', 'Ice', 'Light Drizzle', 'Light Drizzle/Rain', 'Light Freezing Drizzle/Freezing Rain', 'Light Freezing Rain', 'Light Rain', 'Light Rain And Snow', 'Light Snow', 'Lightning Without Thunder', 'Mist', 'Precipitation In Vicinity', 'Rain', 'Rain Showers', 'Sky Coverage Decreasing', 'Sky Coverage Increasing', 'Sky Unchanged', 'Smoke Or Haze', 'Snow', 'Snow And Rain Showers', 'Snow Showers', 'Squalls', 'Thunderstorm', 'Thunderstorm Without Precipitation']

	condition_types = ['Clear', 'Overcast', 'Partially cloudy', 'Rain']

	weather_labels = list(map(lambda val: "weather_type_" + val, weather_types))
	conditions_labels = list(map(lambda val: "condition_type_" + val, condition_types))

	for label in weather_labels:
		df[label] = 0

	for label in conditions_labels:
		df[label] = 0

	for index, row in df.iterrows():
		new_weather_col = [0] * len(weather_types)
		new_condition_col = [0] * len(condition_types)

		weather_features = row['Weather Type']
		conditions = row['Conditions']

		if isinstance(weather_features, str) and weather_features != "":
			types = weather_features.split(", ")
			
			for weather_type in types:
				col = "weather_type_" + weather_type
				df.at[index,col] = 1
		
		if isinstance(conditions, str) and conditions != "":
			types = conditions.split(", ")

			for condition in types:
				col = "condition_type_" + condition
				df.at[index,col] = 1

	del df["Weather Type"]
	del df["Conditions"]

	return df
