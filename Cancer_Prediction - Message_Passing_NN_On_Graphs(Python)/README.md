### Summary ###

Implmenetation of a message passing nueral network to detect cancer. Please note - the molecule construction code was not written by me (the rest of the non test code was!). The model works as follows: Creat molecules from data provided, one hot these molecules (array length 119 for each elemnt in the periodic table), lift one-hotted molecules (pass through dense layer), perform message passing 3x (Each node sends it's lifted infromation to it's neighboring node, then each node collects all information passed to it). This Network achieved a ~72% accuracy in predciting if a given molecule was cancerous. Gradient descent done using classic loss calculation - sparse categorical cross entropy, followed by gradient update.

## Run instructions ##
Dowload all files and run assignment.py. The requirements needed for your local environment to be compatible with this run is listed in the requirements.txt file 
