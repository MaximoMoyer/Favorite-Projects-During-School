import numpy as np
import sys
import tensorflow as tf
from random import seed, shuffle
import sdf_iterator

def read_file(file_name):
    """
    TA provided helper function to read an .sdf file.
    Given the file, this produces a list of all the molecules
    in a file (see molecule.py for the contents of a molecule).

    :param file_name: string, name of data file
    :return: a list of molecules. The nodes are only a list of atomic numbers.
    """
    iterator = sdf_iterator.SdfIterator(file_name)
    mol = iterator.read_molecule()
    molecules = []
    while mol is not None:
        molecules.append(mol)
        mol = iterator.read_molecule()
    return molecules

def get_data(file_name, rd_seed=None):
    """
    Loads the NCI dataset from an sdf file.

    After getting back a list of all the molecules in the .sdf file,
    there's a little more preprocessing to do. First, you need to one hot
    encode the nodes of the molecule to be a 2d numpy array of shape
    (num_atoms, 119) of type np.float32 (see molecule.py for more details).
    After the nodes field has been taken care of, shuffle the list of
    molecules, and return a train/test split of 0.9/0.1.

    :param file_name: string, name of data file
    :param rd_seed the random seed for shuffling
    :return: train_data, test_data. Two lists of shuffled molecules that have had their
    nodes turned into a 2d numpy matrix, and of split 0.9 to 0.1.
    """
    test_fraction = 0.1
    number_of_elements = 119
    seed(rd_seed)
    molecules = read_file(file_name)
    count = 0
    for i in molecules:
        nodes = i.nodes
        vec = np.zeros((len(nodes),number_of_elements))
        rows = np.arange(len(nodes))
        vec[rows,nodes] = 1
        i.nodes = vec.astype(np.float32)
        count +=1

    
    shuffle(molecules)
    train_num = int((1-test_fraction)*len(molecules))
    train = molecules[0:train_num]
    test = molecules[train_num:]

    return train, test
