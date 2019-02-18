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
    file_name_simple = file_name.replace('/', "-") + sys.argv[2]
    df = pd.read_csv(file_name + 'and/' + sys.argv[2] + '/' + avg)
    plt.plot(df['minhashSize'], df['SameItems'], label='and')
    df = pd.read_csv(file_name + 'or/' + sys.argv[2] + '/' + avg)
    plt.plot(df['minhashSize'], df['SameItems'], label='or')
    plt.legend()
plt.savefig('results/' + file_name_simple + '.png')
