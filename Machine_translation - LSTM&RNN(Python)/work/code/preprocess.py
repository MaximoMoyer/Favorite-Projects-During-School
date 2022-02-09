import numpy as np
import tensorflow as tf
import numpy as np

from attenvis import AttentionVis
av = AttentionVis()

PAD_TOKEN = "*PAD*"
STOP_TOKEN = "*STOP*"
START_TOKEN = "*START*"
UNK_TOKEN = "*UNK*"
FRENCH_WINDOW_SIZE = 14
ENGLISH_WINDOW_SIZE = 14

def pad_corpus(french, english):
	"""

	arguments are lists of FRENCH, ENGLISH sentences. Returns [FRENCH-sents, ENGLISH-sents]. The
	text is given an initial "*STOP*".  All sentences are padded with "*STOP*" at
	the end.

	:param french: list of French sentences
	:param english: list of English sentences
	:return: A tuple of: (list of padded sentences for French, list of padded sentences for English)
	"""
	FRENCH_padded_sentences = []
	FRENCH_sentence_lengths = []
	for line in french:
		padded_FRENCH = line[:FRENCH_WINDOW_SIZE]
		padded_FRENCH += [STOP_TOKEN] + [PAD_TOKEN] * (FRENCH_WINDOW_SIZE - len(padded_FRENCH)-1)
		FRENCH_padded_sentences.append(padded_FRENCH)

	ENGLISH_padded_sentences = []
	ENGLISH_sentence_lengths = []
	for line in english:
		padded_ENGLISH = line[:ENGLISH_WINDOW_SIZE]
		padded_ENGLISH = [START_TOKEN] + padded_ENGLISH + [STOP_TOKEN] + [PAD_TOKEN] * (ENGLISH_WINDOW_SIZE - len(padded_ENGLISH)-1)
		ENGLISH_padded_sentences.append(padded_ENGLISH)

	return FRENCH_padded_sentences, ENGLISH_padded_sentences

def build_vocab(sentences):
	"""

  Builds vocab from list of sentences

	:param sentences:  list of sentences, each a list of words
	:return: tuple of (dictionary: word --> unique index, pad_token_idx)
  """
	tokens = []
	for s in sentences: tokens.extend(s)
	all_words = sorted(list(set([STOP_TOKEN,PAD_TOKEN,UNK_TOKEN] + tokens)))

	vocab =  {word:i for i,word in enumerate(all_words)}

	return vocab,vocab[PAD_TOKEN]

def convert_to_id(vocab, sentences):
	"""

  Convert sentences to indexed 

	:param vocab:  dictionary, word --> unique index
	:param sentences:  list of lists of words, each representing padded sentence
	:return: numpy array of integers, with each row representing the word indeces in the corresponding sentences
  """
	return np.stack([[vocab[word] if word in vocab else vocab[UNK_TOKEN] for word in sentence] for sentence in sentences])


def read_data(file_name):
	"""

  Load text data from file

	:param file_name:  string, name of data file
	:return: list of sentences, each a list of words split on whitespace
  """
	text = []
	with open(file_name, 'rt', encoding='latin') as data_file:
		for line in data_file: text.append(line.split())
	return text

@av.get_data_func
def get_data(french_training_file, english_training_file, french_test_file, english_test_file):
	"""
	Use the helper functions in this file to read and parse training and test data, then pad the corpus.
	Then vectorize your train and test data based on your vocabulary dictionaries.

	:param french_training_file: Path to the french training file.
	:param english_training_file: Path to the english training file.
	:param french_test_file: Path to the french test file.
	:param english_test_file: Path to the english test file.
	
	:return: Tuple of train containing:
	(2-d list or array with english training sentences in vectorized/id form [num_sentences x 15] ),
	(2-d list or array with english test sentences in vectorized/id form [num_sentences x 15]),
	(2-d list or array with french training sentences in vectorized/id form [num_sentences x 14]),
	(2-d list or array with french test sentences in vectorized/id form [num_sentences x 14]),
	english vocab (Dict containg word->index mapping),
	french vocab (Dict containg word->index mapping),
	english padding ID (the ID used for *PAD* in the English vocab. This will be used for masking loss)
	"""
	# RETURNS IN ORDER: train_english, test_english, train_french, test_french, english_vocab, french_vocab, eng_padding_index


	#1) Read English and French Data for training and testing
	fTrain = read_data(french_training_file)
	eTrain = read_data(english_training_file)
	fTest = read_data(french_test_file)
	eTest = read_data(english_test_file)
	#2) Pad training data
	trainPad = pad_corpus(fTrain,eTrain)
	#3) Pad testing data
	testPad = pad_corpus(fTest,eTest)
	#4) Build vocab for french
	fVocab = build_vocab(trainPad[0])
	#5) Build vocab for english
	eVocab = build_vocab(trainPad[1])
	#6) Convert training and testing english sentences to list of IDS
	eTrainID = convert_to_id(eVocab[0],trainPad[1])
	eTestID = convert_to_id(eVocab[0],testPad[1])
	#7) Convert training and testing french sentences to list of IDS 
	fTrainID = convert_to_id(fVocab[0],trainPad[0])
	fTestID = convert_to_id(fVocab[0],testPad[0])

	return (eTrainID,eTestID,fTrainID,fTestID,eVocab[0],fVocab[0],eVocab[1])
	