import os
import numpy as np
import tensorflow as tf
import numpy as np
from preprocess import *
from transformer_model import Transformer_Seq2Seq
from rnn_model import RNN_Seq2Seq
import sys
import random

from attenvis import AttentionVis
av = AttentionVis()

def train(model, train_french, train_english, eng_padding_index):
	"""
	Runs through one epoch - all training examples.
	
	:param model: the initialized model to use for forward and backward pass
	:param train_french: french train data (all data for training) of shape (num_sentences, 14)
	:param train_english: english train data (all data for training) of shape (num_sentences, 15)
	:param eng_padding_index: the padding index, the id of *PAD* token. This integer is used when masking padding labels.
	:return: None

	"""

	# For each training step, we pass in the french sentences to be used by the encoder, 
	# and english sentences to be used by the decoder
	# - The english sentences passed to the decoder have the last token in the window removed:
	#	 [STOP CS147 is the best class. STOP *PAD*] --> [STOP CS147 is the best class. STOP] 
	# 
	# - When computing loss, the decoder labels has the first word removed:
	# [STOP CS147 is the best class. STOP] --> [CS147 is the best class. STOP]
	sentences = len(train_french)

	runs = int(sentences/(model.batch_size))

	for i in range(runs):
		Encoder = train_french[i*model.batch_size:(i+1)*model.batch_size,:]
		Decoder = train_english[i*model.batch_size:(i+1)*model.batch_size,:-1]
		Labels = train_english[i*model.batch_size:(i+1)*model.batch_size,1:]
		mask = []
		for k in range(len(Labels)):
			mask.append([0 if x == eng_padding_index else 1 for x in Labels[k]])
		with tf.GradientTape() as tape:
			probs = model.call(Encoder,Decoder)
			loss = model.loss_function(probs, Labels, mask)
		gradients = tape.gradient(loss, model.trainable_variables)
		model.optimizer.apply_gradients(zip(gradients, model.trainable_variables))
		print(i)
	return None

@av.test_func
def test(model, test_french, test_english, eng_padding_index):
	"""
	Runs through one epoch - all testing examples.

	:param model: the initialized model to use for forward and backward pass
	:param test_french: french test data (all data for testing) of shape (num_sentences, 14)
	:param test_english: english test data (all data for testing) of shape (num_sentences, 15)
	:param eng_padding_index: the padding index, the id of *PAD* token. This integer is used when masking padding labels.
	:returns: a tuple containing at index 0 the perplexity of the test set and at index 1 the per symbol accuracy on test set, 
	e.g. (my_perplexity, my_accuracy)
	"""
	sentences = len(test_french)
	runs = int(sentences/(model.batch_size))
	accuracy = 0
	maskSum = 0
	loss = 0
	for i in range(runs):
		print(i)
		Encoder = test_french[i*model.batch_size:(i+1)*model.batch_size,:]
		Decoder = test_english[i*model.batch_size:(i+1)*model.batch_size,:-1]
		Labels = test_english[i*model.batch_size:(i+1)*model.batch_size,1:]
		mask = []
		for k in range(len(Labels)):
			mask.append([0 if x == eng_padding_index else 1 for x in Labels[k]])
		maskSum += float(tf.math.reduce_sum(mask))
		probs = model.call(Encoder,Decoder)
		loss += model.loss_function(probs, Labels, mask)
		accuracy += model.accuracy_function(probs,Labels,mask)*(float(tf.math.reduce_sum(mask)))
	perplexity = tf.math.exp(loss/maskSum)
	accuracy = accuracy/maskSum

		
	print(perplexity,accuracy)
	return perplexity,accuracy	
	return None,None

def main():	
	if len(sys.argv) != 2 or sys.argv[1] not in {"RNN","TRANSFORMER"}:
			print("USAGE: python assignment.py <Model Type>")
			print("<Model Type>: [RNN/TRANSFORMER]")
			exit()

	# Change this to "True" to turn on the attention matrix visualization.
	if sys.argv[1] == "TRANSFORMER":
		av.setup_visualization(enable=True)

	print("Running preprocessing...")
	train_english, test_english, train_french, test_french, english_vocab, french_vocab, eng_padding_index = get_data('../../data/fls.txt','../../data/els.txt','../../data/flt.txt','../../data/elt.txt')
	print("Preprocessing complete.")

	model_args = (FRENCH_WINDOW_SIZE, len(french_vocab), ENGLISH_WINDOW_SIZE, len(english_vocab))
	if sys.argv[1] == "RNN":
		model = RNN_Seq2Seq(*model_args)
	elif sys.argv[1] == "TRANSFORMER":
		model = Transformer_Seq2Seq(*model_args) 
	
	# Train and Test Model for 1 epoch.
	train(model,train_french,train_english,eng_padding_index)
	test(model,train_french,train_english,eng_padding_index)
	



	# Visualize a sample attention matrix from the test set
	# Only takes effect if you enabled visualizations above
	av.show_atten_heatmap()

if __name__ == '__main__':
	main()
