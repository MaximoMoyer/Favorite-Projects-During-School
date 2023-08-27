import pandas as pd

def buckets(data):
# Temperature categorizing
    B0 = data['Temperature'].min()
    B1 = data['Temperature'].quantile(0.33333)
    B2 = data['Temperature'].quantile(0.66666)
    B3 = data['Temperature'].max()
    data['Temperature'] = pd.cut(data['Temperature'],[B0,B1,B2,B3],3, labels=['temp_1', 'temp_2', 'temp_3'], retbins=True)[0]
    a = pd.get_dummies(data['Temperature'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Temperature'], axis=1)

 #Wind Chill categorizing
    B0 = data['Wind Chill'].min()
    B1 = data['Wind Chill'].quantile(0.33333)
    B2 = data['Wind Chill'].quantile(0.66666)
    B3 = data['Wind Chill'].max()
    data['Wind Chill'], bins = pd.cut(data['Wind Chill'],[B0,B1,B2,B3], 3, labels=['WC_1', 'WC_2', 'WC_3'], retbins=True)
    print(bins)
    a = pd.get_dummies(data['Wind Chill'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Wind Chill'], axis=1)

 #Precipitation categorizing
    #hard coded as up to 66th percentile had no rain. Broken down into days with and without rain
    data['Precipitation'],bins = pd.cut(data['Precipitation'],[-.1,.000000000000000001,200],2, labels=['Precip_1', 'Precip_2'], retbins=True)
    print(bins)
    a = pd.get_dummies(data['Precipitation'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Precipitation'], axis=1)


 #Snow_Depth categorizing
    B0 = data['Snow Depth'].min()
    B1 = data['Snow Depth'].quantile(0.33333)
    B2 = data['Snow Depth'].quantile(0.66666)
    B3 = data['Snow Depth'].max()
    data['Snow Depth'],bins = pd.cut(data['Snow Depth'],[-.1,.000000000000000001,200],2, labels=['Snow_Depth_1', 'Snow_Depth_2'], retbins=True)
    print(bins)
    a = pd.get_dummies(data['Snow Depth'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Snow Depth'], axis=1)


  #Wind Speed categorizing
    B0 = data['Wind Speed'].min()
    B1 = data['Wind Speed'].quantile(0.33333)
    B2 = data['Wind Speed'].quantile(0.66666)
    B3 = data['Wind Speed'].max()
    data['Wind Speed'], bins = pd.cut(data['Wind Speed'],[B0,B1,B2,B3],3, labels=['Wind_Speed_1', 'Wind_Speed_2','Wind_Speed_3'], retbins=True)
    print(bins)
    a = pd.get_dummies(data['Wind Speed'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Wind Speed'], axis=1)

 #Wind Gust categorizing
    B0 = data['Wind Gust'].min()
    B1 = data['Wind Gust'].quantile(0.33333)
    B2 = data['Wind Gust'].quantile(0.66666)
    B3 = data['Wind Gust'].max()
    data['Wind Gust'],bins = pd.cut(data['Wind Gust'],[B0,B1,B2,B3],3, labels=['Wind_Gust_1', 'Wind_Gust_2','Wind_Gust_3'], retbins=True)
    print(bins)
    a = pd.get_dummies(data['Wind Gust'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Wind Gust'], axis=1)
 
 #Visibility categorizing
    B0 = data['Visibility'].min()
    B1 = data['Visibility'].quantile(0.33333)
    B2 = data['Visibility'].quantile(0.66666)
    B3 = data['Visibility'].max()
    data['Visibility'],bins = pd.cut(data['Visibility'],[B0,B1,B2,B3],3, labels=['Visibility_1', 'Visibility_2', 'Visibility_3'], retbins=True)
    print(bins)
    a = pd.get_dummies(data['Visibility'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Visibility'], axis=1)

 #Cloud Cover categorizing
    B0 = data['Cloud Cover'].min()
    B1 = data['Cloud Cover'].quantile(0.33333)
    B2 = data['Cloud Cover'].quantile(0.66666)
    B3 = data['Cloud Cover'].max()
    data['Cloud Cover'] = pd.cut(data['Cloud Cover'],[B0,B1,B2,B3],3,labels=['Cloud_Cover_1', 'Cloud_Cover_2', 'Cloud_Cover_3'], retbins=True)[0]
    a = pd.get_dummies(data['Cloud Cover'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Cloud Cover'], axis=1)

 #Relative Humidity categorizing
    B0 = data['Relative Humidity'].min()
    B1 = data['Relative Humidity'].quantile(0.33333)
    B2 = data['Relative Humidity'].quantile(0.66666)
    B3 = data['Relative Humidity'].max()
    data['Relative Humidity'] = pd.cut(data['Relative Humidity'],[B0,B1,B2,B3],3, labels=['Relative_Hum_1', 'Relative_Hum_2', 'Relative_Hum_3'], retbins=True)[0]
    a = pd.get_dummies(data['Relative Humidity'])
    data = pd.concat([data, a], axis = 1)
    data = data.drop(['Relative Humidity'], axis=1)
    
    return data


