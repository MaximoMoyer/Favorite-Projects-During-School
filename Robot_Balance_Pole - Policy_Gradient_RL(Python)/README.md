### Summary ###

This is a deep reinforcement learning project that teaches a robot to balance a pole. It uses two (very similair methods) of reinforcment learning. Both models follow the same structure - generate probabilities for the robot's step given a state, chose the next action probablistically, calculate the reward for this step. The model then discounts these rewards using a classic discount factor methodology. The model then calculates loss - herein lies the difference between the two methods. In Reinforce_With_Baseline you are calculating losses in relation to the average you could have recieved at a given step. In Reinforce, you are calculating the loss w.r.t. traw rewards received.

## Run instructions ##
Simply download all the files and run "python assignment.py "_____"" (Filling in the quotes with either REINFORCE or REINFORCE_WITH_BASELINE). You will see a simulation of a pole being balanced when running, using the most recently tuned network! (This means the robot has learned well!)
