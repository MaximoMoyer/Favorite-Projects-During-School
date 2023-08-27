# Summary
Final Project for Brown Deep Learning (CS1470)

Our project was a structured data classification problem, using blockchain data to build
transaction networks and then forecasting transactions between accounts.

-This repository contains a code and data folder. In pre-pre-processing, we used the script
getfirst100k.py on blockchain data from https://senseable2015-6.mit.edu/bitcoin/ to turn our
data into weighted edges format.

-We then used Node2vec.py to learn and output embeddings and graph structures for each of our
10 later graphs (Note: only uploaded graphs from t = 0 to t = 5 to the repository as graphs past t = 5 became too large). These were stored in Data/Node2Vec_outputs. Predict.py contains the model which can
then take in the saved node embeddings and learn to forecast transactions between 2 accounts.

## Materials ##

- Original paper: [Paper](Media/bitcoin_paper.pdf)

### The materials below are ordered in increasing level of detail they provide into our project

- A short movie (with slighlty blurry resolution) that voices over a 2 minute project description can be found here: [Movie](Media/Crypto_Predicto.mov)

- A one page slide of the project can be found here: [Summary](Media/Slide.pdf)

- (recommended) An in depth report of our whole project and its outcomes can be found here: [Report](Media/Report.pdf)


