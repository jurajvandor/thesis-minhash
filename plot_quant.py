import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
k = [1,5,10,20]
names = ['k = 1', 'k = 5', 'k = 10', 'k = 20']
sizes = [2,3,4,5,6,7,8]

i = 1

for j in range(4):
    plt.subplot(2, 2, i)
    i += 1
    plt.title(names[j])
    plt.tight_layout()
    plt.ylabel('presnosť (%)')
    plt.xlabel('veľkosť MinHashu')
    plt.ylim(70, 89)
    file_name = sys.argv[1]
    file_name_simple = file_name.replace('/', "-")
    file_name += avgs[j]
    df = pd.read_csv(file_name)
    for size in sizes:
        minhash = df.loc[df['minhashSize'] != 0]
        minhash = minhash.loc[minhash['buckets'] == size]
        plt.plot(minhash['minhashSize'], minhash['SameItems']/k[j]*100, label=str(size))
    if j == 3:
        plt.legend()
plt.savefig('results/' + file_name_simple + '.png', dpi=800)
