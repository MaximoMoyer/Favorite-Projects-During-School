# MyProjects

 If I had to chose one project for you to check out it would be Bitcoin_Transaction_Prediction. Here, a variant of a published paper from Georgia Tech on deep graph learning was implemented to predict if two bitcoin accounts will transact given their histories and placement in the transaction graph!

Otherwise, this is a repository that shows a small selection of my favorite ML, Web Development/scraping, and multi-class Java projects from school. This README includes the basic models or skill learned during the project. Each README in the projects describes the model and or application. 

Please note some of these projects have code not written by me, though the majority of each project I did develop.

# Projects in this repository

## ML

**[Bitcoin Transaction Prediction - word2vec, DeepWalks &TemporalTraining](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Bitcoin_Transaction_Prediction)**

- Implimentation of a Georgia Tech paper: structured data classification problem, using blockchain data to build transaction networks and account embeddings and then forecasting transactions between accounts.

**[Fire Prediction - NaiveBayes, DecisionTree & HeatMap](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Fire_Prediction)**

- Using National Fire Incident Reporting Systems Data, joined with the Virtual Weather Crossing API data, to determine what type of fire("Natural Vegetation", "Structural", "Vehicle") is most likely to occur given the weather.

**[Cancer Prediction - MessagePassingNN On Graphs](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Cancer_Prediction)**
- Implementation of a message passing nueral network to detect cancer. The model works as follows: Creat molecules from data provided, one hot these molecules (array length 119 for each elemnt in the periodic table), lift one-hotted molecules (pass through dense layer), perform message passing 3x (each node sends it's lifted information to it's neighboring node, then each node collects all information passed to it). Gradient descent done using classic loss calculation - sparse categorical cross entropy, followed by gradient update.

**[Language Model - Trigram & RNN](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Language_Model)**
- Language prediction model using self made trigram and off-the-shelf RNN (GRU) model.

**[Machine Translation - Seq2Se,LST, & RNN](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Machine_Translation)**
- Model to translate French sentences into English. There are two Seq2Seq models impelemented - an LSTM and RNN. Both models utilize the encoder/decoder approach. This means, we use embeddings of a french sentence to produce a final state output from either an RNN or LSTM encoder. Then, we feed this into the decoder LSTM/RNN as the inital hidden state. Armed with this context, the decoder then feeds the outputted word, back into itself until sentence length is reached. The Decoder part of this model is essentially the same as the RNN section of the language modeling project - after the first word (since each model has full sentence context from the initial hidden state) the decoder is simply predicting the most likely next word.

**[Robot Balance Pole - Policy Gradient RL](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Robot_Balance_Pole)**
- This is a deep reinforcement learning project that teaches a robot to balance a pole. It uses two (very similair methods) of reinforcment learning. Both models follow the same structure - generate probabilities for the robot's step given a state, chose the next action probablistically, calculate the reward for this step. The model then discounts these rewards using a classic discount factor methodology. The model then calculates loss - herein lies the difference between the two methods.

## Web Dev/scraping 

**[Stock DataBase Maker - Web Scraping DataBase Creation](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Stock_DataBase_Maker)**
- Simple project that scrapes Yahoo Finance and pulls data from the IEX trading API to get information on various stocks. Also uses SQL to get various cuts of this data.

**[Netflix Dashboard - Dynamic web dev](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Netflix_Dashboard)**
- This project took real Netflix data from Kaggle, and created an interactive dashboard using HTML, CSS, and Javascript.

## Multi-Class Java:
**[Graph - PageRank & PrimJarnik](https://github.com/MaximoMoyer/Favorite-Projects-During-School/tree/main/Graph_Structure_Creation)**
- This project creates a graph data strcuture from scratch and implements the pagerank (elementary search) algorithm and PrimJarnik (MST) algorithm on the graph.





