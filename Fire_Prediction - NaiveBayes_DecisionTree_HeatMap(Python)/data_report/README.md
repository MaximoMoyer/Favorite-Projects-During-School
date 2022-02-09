### Origin of Data ###

We requested FEMA's NFIRS (National Fire Incident Reporting System) incident report data. We requested this data in October and only recieved it this January. It arrives in a CD and contains many different tables of data relating to both fire and EMS incident reports as well as hundreds of pages of documentation. Any time a 911 call is made that fire/EMS is dispatched to, that incident will (most likely) make its way into this dataset.

The source is highly reputable. However, something to note is that this data is voluntarily self-reported by departments. About 3/4 of all fire departments in the US are included in this dataset.

These incident reports include location data. As part of our analysis, we hope to uncover any correlation between weather and fire incidents. Therefore, we also collected weather data (precipitation, temperature, high, low, humidity, etc...) for each location in our incident dataset using the visualcrossing.com Weather API. This is a highly trusted and commonly used API that pulls on government-backed sources to create historical weather reports (it is a popular alternative to the now closed Dark Sky API).

One concern that we have is about the distribution of fire departments that voluntarily report data. We may find that rural fire stations are less likely to report incidents and are unrepresented in the dataset. This is something we will be able to dig into once we perform more analysis on the incident locations in the report. 

### Details of Data ###

Each year there are about 600k fire incidents and more than 5 million non fire incidents reported in the dataset. We are focusing our initial analysis on fire incidents in the year 2018 (we also have avilable data for 2019 as well if we would ever like to expand our analysis). Therefore, we have about 600k data points in our dataset.

We had to join three tables in the FEMA dataset: 1) fire_incident (all fire incidents), 2) basic_incident (logistical information about when each incident was, what types of vehicles responded, etc...), and 3) and incident_address (containing information about the location of each incident). We joined these tables by first loading each CSV file into a Pandas dataframe and then using the built in merge functionality. We then wrote a python script that loops through each row of our table, queries the virtualcrossing Weather API for the weather and the location/date specified, and we append that weather data to the corresponding row. This results in a table detailing 600k incidents including a variety of useful metadata (location, date, incident type, actions taken in response, apparatus and personel used, cause of incident, presence of hazardous materials, number of buildings involved, information on any cars involved, information about the building [height, damage, grade, number of detectors, etc...], etc...). code/nfirs.py was used to join our FEMA tables and code/weather_scrape.py was used to collect and append our weather data.

Collecting weather data was more difficult than we thought. Since we have 600k rows, assuming an average API response time of 200ms, that takes us around 33 hours. We also had to struggle with rate-limiting and limits to the amount of concurrent calls we can make. This means that our data collection process had to be fault tolerant, as we did not want any error half way through our collection process to compromise all of collection we had alreay performed. Additionally, not every zip code in our FEMA dataset had corresponding whether data available (<1% of rows). Looking at these records, we see that most of these zipcodes are located in Alaska. At the moment, we still include these rows but without the weather data. Since this applies to very few rows, we will most likely ignore these rows when performing our weather-based analysis.

Each incident has a unique key, and there are no duplicate values in our dataset. There are, as previously mentioned, "missing" incidents, as only 3/4 of departments self-report.

Each record in the FEMA dataset contains five "key" values: fire department id, state, incident number, address, and exposure number (some fires affect multiple buildings which each get its own incident report). Together, these four values form a primary key and we never have any missing values for these columns. The remaining columns are self-reported, so we frequently have empty value for those columns depending on the incident (a fire incident will have different columns filled out than a car crash response). However, within those columns, even the voluntarily reported ones, there is no datatype issue or inconsistent formatting.

Although we are not throwing data away, we are, for now, not looking at the 5 million non fire incidents in the dataset, as we want to focus our analysis on the cause of fires.

### Summary ###

One challenge is the sheer number of columns we have. Each column could provide some information that is useful, however, there are so many that it may become difficult to narrow down on the more interesting correlations. Additionally, due to the nature of the dataset, not every column is filled out for every incident report. Not every incident involves a car, for example, so not every incident has any values filled out for the columns relating to information about the vehicles involved in the dataset. Our next steps may be to find ways to meaningfully group our data so that we can provide

Using this data, we want to investigate questions such as what types of weather conditions can increase the likelihoods and destructiveness of house fires, which types of buildings are more succeptible to fires (and what building properties give way to multi-building fires), and more.
