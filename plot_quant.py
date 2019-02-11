import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
buckets = [2,3,4,5,6,7,8]
for avg in avgs:
    plt.ylabel('number of same items')
    plt.xlabel('minhash size')
    file_name = sys.argv[1] + avg

    file_name_simple = file_name.replace('/', "-")

    df = pd.read_csv(file_name)

    for bucket in buckets:
        bucket_table = df.loc[df['buckets'] == bucket]
        plt.plot(bucket_table['minhashSize'], bucket_table['SameItems'], label=str(bucket))
    plt.legend()
    plt.savefig('results/' + file_name_simple + '.png')
    plt.clf()
