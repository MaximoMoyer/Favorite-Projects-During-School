import random
import pandas as pd
import datetime as dt
import numpy as np
from oneHot import oneHotColumns
from Continuous_buckets import buckets
from sklearn.naive_bayes import BernoulliNB

import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report, confusion_matrix

df = pd.read_csv('comb_file.csv')
#columns = ['INC_TYPE','Maximum Temperature', 'Minimum Temperature' 'Temperature', 'Wind Chill',  'Heat Index',  'Precipitation','Snow Depth', 'Wind Speed',  'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']
#just no temperature min and max, and no heat index becuase lack of data
columns = ['INC_TYPE', 'Temperature', 'Wind Chill', 'Precipitation','Snow Depth', 'Wind Speed', 'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']

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
#labels = ['Structure','Mobile_Property','Vehicle', 'Natural_Vegetation', 'Rubbish', 'Special_Outside', 'Cultivated_Vegetation']
labels = ['Structure','Vehicle', 'Natural_Vegetation']
numbers = [111,112,113,114,115,116,117,118,130,131,132,133,134,135,136,137,138,140,141,142,143]
df = df[df.INC_TYPE.isin(numbers)]
#labels = ['Structure','Mobile_Property','Vehicle', 'Natural_Vegetation', 'Rubbish', 'Special_Outside', 'Cultivated_Vegetation']
#df.INC_TYPE = pd.cut(df.INC_TYPE, [110,119,124,139,144,156,165,174],  labels = labels, include_lowest=True)
df.INC_TYPE = pd.cut(df.INC_TYPE, [110,119,139,174],  labels = labels, include_lowest=True)
#droping all rows with null values
df = df.dropna()
#Setting labels equal to the type of fire
y = df.INC_TYPE
print(len(df))
#attributes
columnsX = ['Temperature', 'Wind Chill', 'Precipitation','Snow Depth', 'Wind Speed',  
'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']
X = df[columnsX]

#one hot for categorical variables
X = oneHotColumns(X)
#categorizing continous variables
X = buckets(X)
# X.to_csv('~/Desktop/hi.csv', index = False)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20)

classifier = BernoulliNB()
classifier.fit(X, y)

y_pred = classifier.predict(X_test)

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))

y_train_pred = classifier.predict(X_train)

print(confusion_matrix(y_train, y_train_pred))
print(classification_report(y_train, y_train_pred))
