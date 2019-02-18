import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
sizes = [2,3,4,5,6,7,8]

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
    for size in sizes:
        minhash = df.loc[df['minhashSize'] != 0]
        minhash = minhash.loc[minhash['buckets'] == size]
        plt.plot(minhash['minhashSize'], minhash['SameItems'], label=str(size))
    plt.legend()
plt.savefig('results/' + file_name_simple + '.png', dpi=800)
