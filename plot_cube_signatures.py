import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']

cube_sizes = [5,10,15,20,25,30,35]
time_cubes = [1,2,3,4,5,6,7,8,9,10]

i = 1
for avg in avgs:
    plt.subplot(2, 2, i)
    i += 1
    plt.title(avg)
    plt.tight_layout()
    plt.ylabel('recall')
    plt.xlabel('cube sizes')
    file_name = sys.argv[1]
    file_name_simple = file_name.replace('/', "-")
    file_name += avg
    df = pd.read_csv(file_name)
    df = df.loc[df['minhashSize'] == 0]
    for size in time_cubes:
        sig = df.loc[df['timeCubes'] == size]
        plt.plot(sig['oneDimensionalCuts'], sig['SameItems'], label=str(size))
plt.legend()
plt.savefig('results/' + file_name_simple + 'signatures' + '.png', dpi=800)
