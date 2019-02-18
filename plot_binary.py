import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['avg1.csv', 'avg5.csv', 'avg10.csv', 'avg20.csv']

i = 1
for avg in avgs:
    plt.subplot(2, 2, i)
    i += 1
    plt.title(avg)
    plt.tight_layout()
    plt.ylabel('recall')
    plt.xlabel('minhash size')
    file_name = sys.argv[1]
    file_name_simple = file_name.replace('/', "-")
    file_name += avg
    df = pd.read_csv(file_name)
    minhash = df.loc[df['minhashSize'] != 0]
    df = df.loc[df['minhashSize'] == 0]
    reference = df['SameItems'][6]
    plt.plot([0,2048], [reference, reference], label='binary signature')
    plt.plot(minhash['minhashSize'], minhash['SameItems'], label='minhash')
    plt.legend()
plt.savefig('results/' + file_name_simple + '.png')
