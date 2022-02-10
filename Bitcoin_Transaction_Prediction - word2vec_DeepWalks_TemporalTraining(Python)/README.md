# crypto_predict
Final Project for Brown Deep Learning (CS1470)

Our project was a structured data classification problem, using blockchain data to build
transaction networks and then forecasting transactions between accounts.

-This repository contains a code and data folder. In pre-pre-processing, we used the script
getfirst100k.py on blockchain data from https://senseable2015-6.mit.edu/bitcoin/ to turn our
data into weighted edges format.

-We then used Node2vec.py to learn and output embeddings and graph structures for each of our
10 graphs. These were stored in Data/Node2Vec_outputs. Predict.py contains the model which can
then take in the saved node embeddings and learn to forecast transactions between 2 accounts.

## Materials ##

- Paper impelemented here: Paper

# The materials below are ordered in increasing level of detail that is provided into how our code works #

- A short movie (with slighlty blurry resolution) that voices over a 2 minute project description can be found here: Movie

- A one page slides of the project can be found here: Summary

- (recommended) An in depth report of our outcomes can be found here: Report


