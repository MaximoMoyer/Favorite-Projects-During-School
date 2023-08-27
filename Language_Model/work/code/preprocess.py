import tensorflow as tf
import numpy as np
from functools import reduce


def get_data(train_file, test_file):
    """
    Read and parse the train and test file line by line, then tokenize the sentences to build the train and test data separately.
    Create a vocabulary dictionary that maps all the unique tokens from your train and test data as keys to a unique integer value.
    Then vectorize your train and test data based on your vocabulary dictionary.

    :param train_file: Path to the training file.
    :param test_file: Path to the test file.
    :return: Tuple of train (1-d list or array with training words in vectorized/id form), test (1-d list or array with testing words in vectorized/id form), vocabulary (Dict containg index->word mapping)
    """
    def readFile(fileName):
        fileObj = open(fileName, "r") #opens the file in read mode
        words = fileObj.read().splitlines() #puts the file into an array
        fileObj.close()
        return words
    
    
    trainArray = readFile(train_file)
    testArray = readFile(test_file)
    splitTrain = []
    splitTest = []
    

    for items in trainArray:
        temp = items.split()
        for words in temp:
            splitTrain.append(words)
    for items in testArray:
        temp = items.split()
        for words in temp:
            splitTest.append(words)

    trainSet = set(splitTrain)
    dic = {}
    B = 0
    for A in trainSet:
        print(A)
        dic[A] = B
        B +=1

    indicesTrain = []
    indicesTest = []
    for A in splitTrain:
        indicesTrain.append(dic.get(A))
    for A in splitTest:
        if dic.get(A,None) is not None:
            indicesTest.append(dic.get(A))

    return(indicesTrain,indicesTest,dic)