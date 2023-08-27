import tensorflow as tf
import numpy as np
from tensorflow.keras import Model
from functools import reduce
from preprocess import get_data
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'



class Model(tf.keras.Model):
    def __init__(self, vocab_size):
        """
        The Model class predicts the next words in a sequence.

        :param vocab_size: The number of unique words in the data
        """

        super(Model, self).__init__()

        self.vocab_size = vocab_size
        self.embedding_size = 30
        self.batch_size = 100
        self.optimizer = tf.keras.optimizers.Adam(learning_rate =.003)

        self.E = tf.Variable(tf.random.truncated_normal([self.vocab_size,self.embedding_size],stddev=.1))
        self.W = tf.Variable(tf.random.truncated_normal([2*self.embedding_size,vocab_size],stddev=.1))
        self.b = tf.Variable(tf.random.truncated_normal([self.vocab_size],stddev=.1))

    def call(self, inputs):
        """
        You must use an embedding layer as the first layer of your network (i.e. tf.nn.embedding_lookup)
        :param inputs: word ids of shape (batch_size, 2)
        :return: probs: The batch element probabilities as a tensor of shape (batch_size, vocab_size)
        """

        embedding1 = tf.nn.embedding_lookup(self.E,inputs[:,0])
        embedding2 = tf.nn.embedding_lookup(self.E,inputs[:,1])
        embedding = tf.concat([embedding1,embedding2],-1)
    
        logits = tf.nn.bias_add(tf.matmul(embedding,self.W),self.b)
        
        probMatrix  = tf.nn.softmax(logits)

        return probMatrix


    def loss_function(self, probs, labels):
        """
        Calculates average cross entropy sequence to sequence loss of the prediction
        
        :param probs: a matrix of shape (batch_size, vocab_size)
        :return: the loss of the model as a tensor of size 1
        """
        loss = tf.keras.losses.sparse_categorical_crossentropy(labels, probs)
        return tf.math.reduce_mean(loss)


def train(model, train_input, train_labels):
    """
    Runs through one epoch - all training examples. 
    You should take the train input and shape them into groups of two words.
    Remember to shuffle your inputs and labels - ensure that they are shuffled in the same order. 
    Also you should batch your input and labels here.
    :param model: the initilized model to use for forward and backward pass
    :param train_input: train inputs (all inputs for training) of shape (num_inputs,2)
    :param train_input: train labels (all labels for training) of shape (num_inputs,)
    :return: None
    """
    runs = int(np.size(train_input)/model.batch_size)
    
    shuffle_array = tf.range(0, np.shape(train_input)[0], 1)
    shuffle_array = tf.random.shuffle(shuffle_array)
    train_input[:,0] = tf.gather(train_input[:,0], shuffle_array)
    train_input[:,1] = tf.gather(train_input[:,1], shuffle_array)
    train_labels = tf.gather(train_labels, shuffle_array)
    

    for i in range(runs):
        inputs = train_input[i*model.batch_size:(i+1)*model.batch_size,:]
        labels = train_labels[i*model.batch_size:(i+1)*model.batch_size]
        with tf.GradientTape() as tape:
            probs = model.call(inputs)
            loss = model.loss_function(probs,labels)
        gradients = tape.gradient(loss, model.trainable_variables)
        model.optimizer.apply_gradients(zip(gradients, model.trainable_variables))
    
    return None


def test(model, test_input, test_labels):
    """
    Runs through all test examples. You should take the test input and shape them into groups of two words.
    And test input should be batched here as well.
    :param model: the trained model to use for prediction
    :param test_input: train inputs (all inputs for testing) of shape (num_inputs,2)
    :param test_input: train labels (all labels for testing) of shape (num_inputs,)
    :returns: perplexity of the test set
    """
    runs = int(np.size(test_input[:,0])/model.batch_size)
    loss = 0
    for i in range(runs):
        inputs = test_input[i*model.batch_size:(i+1)*model.batch_size,:]
        labels = test_labels[i*model.batch_size:(i+1)*model.batch_size]
        probs = model.call(inputs)
        loss += model.loss_function(probs,labels)
        
    return tf.math.exp(tf.constant(loss/runs))


def generate_sentence(word1, word2, length, vocab, model):
    """
    Given initial 2 words, print out predicted sentence of targeted length.

    :param word1: string, first word
    :param word2: string, second word
    :param length: int, desired sentence length
    :param vocab: dictionary, word to id mapping
    :param model: trained trigram model

    """

    #This is a deterministic, argmax sentence generation

    reverse_vocab = {idx: word for word, idx in vocab.items()}
    output_string = np.zeros((1, length), dtype=np.int)
    output_string[:, :2] = vocab[word1], vocab[word2]

    for end in range(2, length):
        start = end - 2
        output_string[:, end] = np.argmax(
            model(output_string[:, start:end]), axis=1)
    text = [reverse_vocab[i] for i in list(output_string[0])]

    print(" ".join(text))


def main():
    data = get_data('../../data/train.txt','../../data/test.txt')
    trainLen = np.size(data[0]) - 1
    testLen = np.size(data[1]) - 1 
    
    trainInputs = np.zeros((trainLen,2))
    testInputs = np.zeros((testLen,2))
    
    trainLabels = np.zeros(trainLen)
    testLabels = np.zeros(testLen)

    model = Model(len(data[2]))
    
    for i in range(np.size(data[0]) - 2) :
        trainInputs[i,0] = data[0][i]
        trainInputs[i,1] = data[0][i+1]
        trainLabels[i] = data[0][i+2]

    for i in range(np.size(data[1]) - 2):
        testInputs[i,0] = data[1][i]
        testInputs[i,1] = data[1][i+1]
        testLabels[i] = data[1][i+2]

    trainInputs = trainInputs.astype(int)
    trainLabels = trainLabels.astype(int)
    testInputs = testInputs.astype(int)
    testLabels = testLabels.astype(int)
    train(model,trainInputs,trainLabels)
    print(test(model,testInputs,testLabels))

if __name__ == '__main__':
    main()
