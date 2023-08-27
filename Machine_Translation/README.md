# Summary #
Model to translate French sentences into English. There are two Seq2Seq models impelemented - an LSTM and RNN. Both models utilize the encoder/decoder approach. This means, we use embeddings of a french sentence to produce a final state output from either an RNN or LSTM encoder. Then, we feed this into the decoder LSTM/RNN as the inital hidden state. Armed with this context, the decoder then feeds the outputted word, back into itself until sentence length is reached. The Decoder part of this model is essentially the same as the language modeling assignment - after the first word (since each model has full sentence context from the initial hidden state) the decoder is simply predicting the most likely next word.

## Running Instructions ##
Download all files and run "python assignment _" Where you fill in the blank with "RNN" or "TRANSFORMER". 
pip install -r requirements.txt to get all requirements!
