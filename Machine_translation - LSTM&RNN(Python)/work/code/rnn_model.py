import numpy as np
import tensorflow as tf

class RNN_Seq2Seq(tf.keras.Model):
	def __init__(self, french_window_size, french_vocab_size, english_window_size, english_vocab_size):
		super(RNN_Seq2Seq, self).__init__()
		self.french_vocab_size = french_vocab_size # The size of the french vocab
		self.english_vocab_size = english_vocab_size # The size of the english vocab

		self.french_window_size = french_window_size # The french window size
		self.english_window_size = english_window_size # The english window size


		# Define any hyperparameters

		# Define batch size and optimizer/learning rate
		self.batch_size = 100 # You can change this
		self.embedding_size = 120 # You should change 
		self.RNNUnits = 128
	
		# Define embeddings, encoder, decoder, and feed forward layers
		self.EF  = tf.Variable(tf.random.normal([self.french_vocab_size,self.embedding_size],stddev=.01))
		self.EE  = tf.Variable(tf.random.normal([self.english_vocab_size,self.embedding_size],stddev=.01))
		self.optimizer = tf.keras.optimizers.Adam(learning_rate =.01)
		self.RNNen = tf.keras.layers.GRU(self.RNNUnits,return_sequences = True, return_state = True)
		self.RNNde = tf.keras.layers.GRU(self.RNNUnits,return_sequences = True, return_state = True)
		self.forward2 = tf.keras.layers.Dense(english_vocab_size,activation = 'softmax')



	@tf.function
	def call(self, encoder_input, decoder_input):
		"""
		:param encoder_input: batched ids corresponding to french sentences
		:param decoder_input: batched ids corresponding to english sentences
		:return prbs: The 3d probabilities as a tensor, [batch_size x window_size x english_vocab_size]
		"""
	
		#Pass  french sentence embeddings to  encoder
		embedding = tf.nn.embedding_lookup(self.EF,encoder_input[:,:])
		outputs1, final_state1 = self.RNNen(embedding, initial_state = None)
		#Pass english sentence embeddings, and final state of  encoder, to  decoder
		embedding = tf.nn.embedding_lookup(self.EE,decoder_input[:,:])
		outputs2, final_state2 = self.RNNde(embedding,initial_state = final_state1)
		#Apply dense layer(s) to the decoder out to generate probabilities
		probs = self.forward2(outputs2)
		


		return probs

	def accuracy_function(self, prbs, labels, mask):
		"""

		Computes the batch accuracy
		
		:param prbs:  float tensor, word prediction probabilities [batch_size x window_size x english_vocab_size]
		:param labels:  integer tensor, word prediction labels [batch_size x window_size]
		:param mask:  tensor that acts as a padding mask [batch_size x window_size]
		:return: scalar tensor of accuracy of the batch between 0 and 1
		"""

		decoded_symbols = tf.argmax(input=prbs, axis=2)
		accuracy = tf.reduce_mean(tf.boolean_mask(tf.cast(tf.equal(decoded_symbols, labels), dtype=tf.float32),mask))
		return accuracy


	def loss_function(self, prbs, labels, mask):
		"""
		Calculates the total model cross-entropy loss after one forward pass. 
		Please use reduce sum here instead of reduce mean to make things easier in calculating per symbol accuracy.
		
		:param prbs:  float tensor, word prediction probabilities [batch_size x window_size x english_vocab_size]
		:param labels:  integer tensor, word prediction labels [batch_size x window_size]
		:param mask:  tensor that acts as a padding mask [batch_size x window_size]
		:return: the loss of the model as a tensor
		"""
		masked = tf.math.multiply(mask,tf.keras.losses.sparse_categorical_crossentropy(labels,prbs))
		return tf.math.reduce_sum(masked)


