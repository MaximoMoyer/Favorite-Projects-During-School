import tensorflow as tf
import numpy as np
from tensorflow.keras import Model
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
        self.window_size = 20 # DO NOT CHANGE!
        self.embedding_size = 60
        self.batch_size = 20
        self.RNNUnits = 128

        self.E = tf.Variable(tf.random.normal([self.vocab_size,self.embedding_size],stddev=.1))
        self.optimizer = tf.keras.optimizers.Adam(learning_rate =.003)
        self.RNN = tf.keras.layers.GRU(self.RNNUnits,return_sequences = True, return_state = True)
        self.forward2 = tf.keras.layers.Dense(vocab_size,activation = 'softmax')
       

    def call(self, inputs, initial_state):
        """
        - You must use an embedding layer as the first layer of your network (i.e. tf.nn.embedding_lookup)
        - You must use an LSTM or GRU as the next layer.
        :param inputs: word ids of shape (batch_size, window_size)
        :param initial_state: 2-d array of shape (batch_size, rnn_size) as a tensor
        :return: the batch element probabilities as a tensor, a final_state (Note 1: If you use an LSTM, the final_state will be the last two RNN outputs, 
        Note 2: We only need to use the initial state during generation)
        using LSTM and only the probabilites as a tensor and a final_state as a tensor when using GRU 
        """
        
        embedding= tf.nn.embedding_lookup(self.E,inputs[:,:])

        
        outputs, final_state = self.RNN(embedding, initial_state = initial_state)
        
        probs = self.forward2(outputs)
        
        return probs, final_state

    def loss(self, probs, labels):
        """
        Calculates average cross entropy sequence to sequence loss of the prediction
        
        NOTE: You have to use np.reduce_mean and not np.reduce_sum when calculating your loss

        :param logits: a matrix of shape (batch_size, window_size, vocab_size) as a tensor
        :param labels: matrix of shape (batch_size, window_size) containing the labels
        :return: the loss of the model as a tensor of size 1
        """
        return tf.math.reduce_mean(tf.keras.losses.sparse_categorical_crossentropy(labels,probs))


def train(model, train_inputs, train_labels):
    """
    Runs through one epoch - all training examples.

    :param model: the initilized model to use for forward and backward pass
    :param train_inputs: train inputs (all inputs for training) of shape (num_inputs,)
    :param train_labels: train labels (all labels for training) of shape (num_labels,)
    :return: None
    """

    runs = int(len(train_inputs)/(model.batch_size*model.window_size))
    
    sliceSize = model.batch_size*model.window_size

    for i in range(runs):
        print(i)
        inputSlice = train_inputs[i*sliceSize:(i+1)*sliceSize]
        inputSlice = tf.reshape(inputSlice,(model.batch_size,model.window_size))
        labelSlice = train_labels[i*sliceSize:(i+1)*sliceSize]
        labelSlice = tf.reshape(labelSlice,(model.batch_size,model.window_size))
        with tf.GradientTape() as tape:
            probs, final_state = model.call(inputSlice,None)
            loss = model.loss(probs,labelSlice)
        gradients = tape.gradient(loss, model.trainable_variables)
        model.optimizer.apply_gradients(zip(gradients, model.trainable_variables))
        print(loss)
    return None


def test(model, test_inputs, test_labels):
    """
    Runs through one epoch - all testing examples

    :param model: the trained model to use for prediction
    :param test_inputs: train inputs (all inputs for testing) of shape (num_inputs,)
    :param test_labels: train labels (all labels for testing) of shape (num_labels,)
    :returns: perplexity of the test set
    """
    runs = int(np.size(test_inputs)/(model.batch_size*model.window_size))
    loss = 0

    inputs = np.zeros((model.batch_size,model.window_size))
    labels = np.zeros((model.batch_size,model.window_size))
    sliceSize = model.batch_size*model.window_size
    for i in range(runs):
        print(i)
        inputSlice = test_inputs[i*sliceSize:(i+1)*sliceSize]
        inputSlice = tf.reshape(inputSlice,(model.batch_size,model.window_size))
        labelSlice = test_labels[i*sliceSize:(i+1)*sliceSize]
        labelSlice = tf.reshape(labelSlice,(model.batch_size,model.window_size))
        
        probs, final_state = model.call(inputSlice,None)
        loss += model.loss(probs,labelSlice)
        
    return tf.math.exp(tf.constant(loss/runs))


    pass  


def generate_sentence(word1, length, vocab, model, sample_n=10):
    """
    Takes a model, vocab, selects from the most likely next word from the model's distribution

    :param model: trained RNN model
    :param vocab: dictionary, word to id mapping
    :return: None
    """

    reverse_vocab = {idx: word for word, idx in vocab.items()}
    previous_state = None

    first_string = word1
    first_word_index = vocab[word1]
    next_input = [[first_word_index]]
    text = [first_string]

    for i in range(length):
        logits, previous_state = model.call(next_input, previous_state)
        logits = np.array(logits[0,0,:])
        top_n = np.argsort(logits)[-sample_n:]
        n_logits = np.exp(logits[top_n])/np.exp(logits[top_n]).sum()
        out_index = np.random.choice(top_n,p=n_logits)

        text.append(reverse_vocab[out_index])
        next_input = [[out_index]]

    print(" ".join(text))


def main():
    data = get_data('../../data/train.txt','../../data/test.txt')

    train_x = data[0][0:len(data[0])-1]
    train_y = data[0][1:len(data[0])]

    test_x = data[1][0:len(data[1])-1]
    test_y = data[1][1:len(data[1])]
    
    model = Model(len(data[2]))

    train(model,train_x,train_y)
    print(test(model,test_x,test_y))
    

if __name__ == '__main__':
    main()
