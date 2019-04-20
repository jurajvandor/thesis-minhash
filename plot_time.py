import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

k = [1,5,10,20]
avgs = ['avg1.csv', 'avg5.csv', 'avg10.csv', 'avg20.csv']
names = ['k = 1', 'k = 5', 'k = 10', 'k = 20']


for i in range(4):
    plt.tight_layout()
    plt.ylabel('čas (ms)')
    plt.xlabel('veľkosť MinHashu')
     #plt.ylim(20, 50)
    file_name = sys.argv[1]
    file_name_simple = file_name.replace('/', "-")
    file_name += avgs[i]
    df = pd.read_csv(file_name)
    minhash = df.loc[df['minhashSize'] != 0]
    df = df.loc[df['minhashSize'] == 0]
    plt.plot(minhash['minhashSize'], minhash['FirstTime'], label=names[i])
    plt.legend()
plt.savefig('results/' + file_name_simple + 'time.png', dpi=800)
