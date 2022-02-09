import pandas as pd
from scipy import stats
import math

def get_averages(incident):
	spring_average = 0
	summer_average = 0
	fall_average = 0
	winter_average = 0
	for i in range(len(incident)):
		if incident[i][1] == "Spring":
			spring_average += 1
		elif incident[i][1] == "Summer":
			summer_average += 1
		elif incident[i][1] == "Fall":
			fall_average += 1
		else:
			winter_average += 1
	averages = []
	averages.append(spring_average)
	averages.append(summer_average)
	averages.append(fall_average)
	averages.append(winter_average)
	return averages




if __name__=='__main__':
	df = pd.read_csv('/Users/chotooamin/desktop/cstuff/finalproj/comb_file.csv')
	#columns = ['INC_TYPE','Maximum Temperature', 'Minimum Temperature' 'Temperature', 'Wind Chill',  'Heat Index',  'Precipitation','Snow Depth', 'Wind Speed',  'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']
	#just no temperature min and max, and no heat index becuase lack of data
	columns = ['INC_TYPE','yyyy-mm-dd','Temperature', 'Wind Chill','Precipitation','Snow Depth','Wind Speed','Wind Gust','Visibility','Cloud Cover','Relative Humidity','Weather Type','Conditions']

	#111-118 Structure
	#120-123 fire in mobile property used as structure
	#130-138 Mobile (vehicle) fire
	#140-143 Natural Vegetation fire
	#150-155 Rubbish fire
	#160-164 Special outside fire
	#170-173 cultivated vegetation fire
	#100 other

	#gettin Incidient type and all weather attributes as columns in our dataframe
	df = df[columns]
	#dropping all instances of "other" fires
	df = df[df.INC_TYPE != 100]
	df = df[df.INC_TYPE != None]
	#looping over each value in the dataset and setting it's incident type to more generic string
	labels = ['Structure','Mobile_Property','Vehicle', 'Natural_Vegetation', 'Rubbish', 'Special_Outside', 'Cultivated_Vegetation']
	df.INC_TYPE = pd.cut(df.INC_TYPE, [110,119,124,139,144,156,165,174],labels = labels, include_lowest=True)

	grouped_df = df.groupby('INC_TYPE')
	grouped_date = grouped_df['yyyy-mm-dd'].apply(list)
	grouped_incident = grouped_df['INC_TYPE'].apply(list)
	annual_averages = []
	annual_averages.append(len(grouped_incident[0]))
	annual_averages.append(len(grouped_incident[1]))
	annual_averages.append(len(grouped_incident[2]))
	annual_averages.append(len(grouped_incident[3]))
	annual_averages.append(len(grouped_incident[4]))
	annual_averages.append(len(grouped_incident[5]))
	annual_averages.append(len(grouped_incident[6]))
	combined = []

	for i in range(len(grouped_incident)):
		incident = []
		for j in range(len(grouped_incident[i])):
			date = str(grouped_date[i][j])
			date = date.split('-')
			month = int(date[1])
			day = int(date[2])
			if (month == 3 and day >= 21) or (month == 6 and day < 21) or (month > 3 and month < 6):
				season = "Spring"
			elif (month == 6 and day >= 21) or (month == 9 and day < 21) or (month > 6 and month < 9):
				season = "Summer"
			elif (month == 9 and day >= 21) or (month == 12 and day < 21) or (month > 9 and month < 12):
				season = "Fall"
			else:
				season = "Winter"
			incident.append([grouped_incident[i][j], season])
		combined.append(incident)


	for i in range(len(annual_averages)):
		season_averages = get_averages(combined[i])
		print("incident count: ", season_averages)



