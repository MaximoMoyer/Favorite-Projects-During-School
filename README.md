# MyProjects

This is a repository that shows a small selection of my ML, Web Development/scraping, and multi-class Java projects. If I had to chose one project for you to click on it would be Bitcoin_Transaction_Prediction - this was a cool one!

## Projects in this repository

### ML

**Fire_Prediction - NaiveBayes_DecisionTree_HeatMap(Python)**

- Using National Fire Incident Reporting Systems Data, joined with the Virtual Weather Crossing API data, to determine what type of fire("Natural Vegetation", "Structural", "Vehicle") is most likely to occur given the weather.

**Bitcoin_Transaction_Prediction - word2vec_DeepWalks_TemporalTraining(Python)**

- Implimentation of a Georgia Tech paper: structured data classification problem, using blockchain data to build transaction networks and account embeddings and then forecasting transactions between accounts.

**Cancer_Prediction - Message_Passing_NN_On_Graphs(Python)**
- Implementation of a message passing nueral network to detect cancer. The model works as follows: Creat molecules from data provided, one hot these molecules (array length 119 for each elemnt in the periodic table), lift one-hotted molecules (pass through dense layer), perform message passing 3x (each node sends it's lifted information to it's neighboring node, then each node collects all information passed to it). Gradient descent done using classic loss calculation - sparse categorical cross entropy, followed by gradient update.

**Language_Model - Trigram&RNN(Python)**
- Language prediction model using self made trigram and off the self RNN (GRU) model.

**Machine_Translation-Seq2Seq_LSTM&RNN(Python)**
- Under Construction

**Robot_Balance_Pole - Policy_Gradient_RL(Python)**
- This is a deep reinforcement learning project that teaches a robot to balance a pole. It uses two (very similair methods) of reinforcment learning. Both models follow the same structure - generate probabilities for the robot's step given a state, chose the next action probablistically, calculate the reward for this step. The model then discounts these rewards using a classic discount factor methodology. The model then calculates loss - herein lies the difference between the two methods.

### Web Dev/scraping

**Stock_DataBase_Maker - Web_Scraping&DataBase_Creation(Python/SQL)**
- Simple project that scrapes Yahoo Finance and pulls data from the IEX trading API to get information on various stocks. Also uses SQL to get various cuts of this data.

**Netflix_Dashboard - (CSS,HTML,JS)**
- This project took real Netflix data from Kaggle, and created an interactive dashboard using HTML, CSS, and Javascript.

### Multi-Class Java:
**Graph - PageRank/PrimJarnik(Java)**
- This project creates a graph data strcuture from scratch and implements the pagerank (elementary search) algorithm and PrimJarnik (MST) algorithm on the graph.





