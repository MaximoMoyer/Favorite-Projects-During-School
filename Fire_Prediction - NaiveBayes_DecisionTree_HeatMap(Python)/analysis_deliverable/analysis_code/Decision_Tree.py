import random
import pandas as pd
import datetime as dt
import numpy as np
from oneHot import oneHotColumns

import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import classification_report, confusion_matrix
from sklearn import tree


df = pd.read_csv('comb_file.csv')
#columns = ['INC_TYPE','Maximum Temperature', 'Minimum Temperature' 'Temperature', 'Wind Chill',  'Heat Index',  'Precipitation','Snow Depth', 'Wind Speed',  'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']
#just no temperature min and max, and no heat index becuase lack of data
columns = ['INC_TYPE', 'Temperature', 'Wind Chill', 'Precipitation','Snow Depth', 'Wind Speed',  
'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']

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
#df.INC_TYPE = pd.cut(df.INC_TYPE, [110,119,124,139,144,156,165,174],  labels = labels, include_lowest=True)
df.INC_TYPE = pd.cut(df.INC_TYPE, [110,119,139,174],  labels = labels, include_lowest=True)
#droping all rows with null values
df = df.dropna()
#Setting labels equal to the type of fire
y = df.INC_TYPE

#attributes
columnsX = ['Temperature', 'Wind Chill', 'Precipitation','Snow Depth', 'Wind Speed',  
'Wind Gust', 'Visibility', 'Cloud Cover', 'Relative Humidity', 'Weather Type','Conditions']
X = df[columnsX]

count = 0
counts = np.zeros(len(labels))
for index,row in df.iterrows():
    count += 1
    for i in range(0,len(labels)):
        if labels[i] == row.INC_TYPE:
            counts[i] += 1

counts = counts/count

X = oneHotColumns(X)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20)


classifier = DecisionTreeClassifier(max_depth=3)
classifier.fit(X_train, y_train)

y_pred = classifier.predict(X_test)

print(confusion_matrix(y_test, y_pred))
print(classification_report(y_test, y_pred))

y_train_pred = classifier.predict(X_train)

print(confusion_matrix(y_train, y_train_pred))
print(classification_report(y_train, y_train_pred))


fig = plt.figure(figsize=(75,15))
_ = tree.plot_tree(classifier,
					fontsize = 14,
                   feature_names=X.columns,
                   label = False,  
                   class_names=["Natural Vegetation", "Structure", "Vehicle"],
                   filled=True,
                   impurity = False,
                   node_ids = False,
                   proportion = False)
plt.show()





            

#With only dropping null INC_Type (and disinluding heat index):
#[0.36751307 0.01899024 0.25404065 0.2379697  0.07167502 0.04341743 0.00639388]

#dropping any row with a null
#[0.45540083 0.02061822 0.24988955 0.17728396 0.052151   0.03964916 0.00500728]

#wind chill/wind gust other ones that were somehwat sparse




