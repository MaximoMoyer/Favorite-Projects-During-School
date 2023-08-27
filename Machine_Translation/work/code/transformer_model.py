import numpy as np
import tensorflow as tf
import transformer_funcs as transformer

from attenvis import AttentionVis

av = AttentionVis()

class Transformer_Seq2Seq(tf.keras.Model):
	def __init__(self, french_window_size, french_vocab_size, english_window_size, english_vocab_size):

		super(Transformer_Seq2Seq, self).__init__()

		self.french_vocab_size = french_vocab_size # The size of the french vocab
		self.english_vocab_size = english_vocab_size # The size of the english vocab

		self.french_window_size = french_window_size # The french window size
		self.english_window_size = english_window_size # The english window size


		#Define any hyperparameters
		#Define embeddings, encoder, decoder, and feed forward layers

		# Define batch size and optimizer/learning rate
		self.batch_size = 100
		self.embedding_size = 200
		self.optimizer = tf.keras.optimizers.Adam(learning_rate =.001)

		# Define english and french embedding layers:
		self.EF  = tf.Variable(tf.random.normal([self.french_vocab_size,self.embedding_size],stddev=.01))
		self.EE  = tf.Variable(tf.random.normal([self.english_vocab_size,self.embedding_size],stddev=.01))
		# Create positional encoder layers
		self.PE = transformer.Position_Encoding_Layer(french_window_size,self.embedding_size)
		self.PF = transformer.Position_Encoding_Layer(french_window_size,self.embedding_size)
		# Define encoder and decoder layers:
		self.Encoder = transformer.Transformer_Block(self.embedding_size,is_decoder = False, multi_headed = False)
		#self.Encoder2 = transformer.Transformer_Block(self.embedding_size,is_decoder = False, multi_headed = False)
		self.Decoder = transformer.Transformer_Block(self.embedding_size,is_decoder = True, multi_headed = False)
		# Define dense layer(s)
		#self.forward1 = tf.keras.layers.Dense(english_vocab_size,activation = 'softmax')
		self.forward2 = tf.keras.layers.Dense(english_vocab_size,activation = 'softmax')

	@tf.function
	def call(self, encoder_input, decoder_input):
		"""
		:param encoder_input: batched ids corresponding to french sentences
		:param decoder_input: batched ids corresponding to english sentences
		:return prbs: The 3d probabilities as a tensor, [batch_size x window_size x english_vocab_size]
		"""
		#Add the positional embeddings to french sentence embeddings
		embedding1 = tf.nn.embedding_lookup(self.EF,encoder_input)
		embedding1  = self.PF(embedding1)
		#Pass the french sentence embeddings to the encoder
		eReturn = self.Encoder.call(embedding1)
		#Add positional embeddings to the english sentence embeddings
		embedding2 = tf.nn.embedding_lookup(self.EE,decoder_input)
		embedding2 = self.PE(embedding2)
		#Pass the english embeddings and output of your encoder, to the decoder
		dReturn = self.Decoder.call(embedding2,eReturn)
		
		#Apply dense layer(s) to the decoder out to generate probabilities
		forward = self.forward2(dReturn)
		
		return forward

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
		Calculates the model cross-entropy loss after one forward pass

		:param prbs:  float tensor, word prediction probabilities [batch_size x window_size x english_vocab_size]
		:param labels:  integer tensor, word prediction labels [batch_size x window_size]
		:param mask:  tensor that acts as a padding mask [batch_size x window_size]
		:return: the loss of the model as a tensor
		"""
		masked = tf.math.multiply(mask,tf.keras.losses.sparse_categorical_crossentropy(labels,prbs))
		return tf.math.reduce_sum(masked)	

	@av.call_func
	def __call__(self, *args, **kwargs):
		return super(Transformer_Seq2Seq, self).__call__(*args, **kwargs)