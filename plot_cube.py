import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
cube_sizes = [20]
time_cubes = [5,6,7,8]

i = 1
for cube_size in cube_sizes:
    for avg in avgs:
        plt.subplot(2, 2, i)
        plt.tight_layout()
        i += 1
        plt.title(avg)
        plt.ylabel('recall')
        plt.xlabel('cube sizes')
        file_name = sys.argv[1]
        file_name_simple = file_name.replace('/', "-")
        file_name += avg
        df = pd.read_csv(file_name)
        df = df.loc[df['oneDimensionalCuts'] == cube_size]
        df = df.loc[df['minhashSize'] != 0]
        for size in time_cubes:
            minhash = df.loc[df['timeCubes'] == size]
            plt.plot(minhash['minhashSize'], minhash['SameItems'], label=str(size))
    plt.legend()
    plt.savefig('results/' + file_name_simple + 'minhash-'+ str(cube_size) + '.png', dpi=800)
    plt.clf()
    i = 1
