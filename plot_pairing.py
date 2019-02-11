import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['avg1.csv', 'avg5.csv', 'avg10.csv', 'avg20.csv']
for avg in avgs:
    plt.ylabel('number of same items')
    plt.xlabel('minhash size')
    file_name = sys.argv[1]

    file_name_simple = file_name.replace('/', "-")

    df = pd.read_csv(file_name + 'and/' + sys.argv[2] + '/' + avg)
    plt.plot(df['minhashSize'], df['SameItems'], label='and')
    df = pd.read_csv(file_name + 'or/' + sys.argv[2] + '/' + avg)
    plt.plot(df['minhashSize'], df['SameItems'], label='or')
    plt.legend()
    plt.savefig('results/' + file_name_simple + '-' +sys.argv[2] + '-' + avg + '.png')
    plt.clf()
