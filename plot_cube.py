import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
names = ['k = 1', 'k = 5', 'k = 10', 'k = 20']
k = [1,5,10,20]

cube_sizes = [15]
time_cubes = [5,6,7,8]

i = 1
for cube_size in cube_sizes:
    for j in range(4):
        plt.subplot(2, 2, i)
        plt.tight_layout()
        i += 1
        plt.title(names[j])
        plt.ylabel('presnosť (%)')
        plt.xlabel('veľkosť MinHashu')
        plt.ylim(35, 85)
        file_name = sys.argv[1]
        file_name_simple = file_name.replace('/', "-")
        file_name += avgs[j]
        df = pd.read_csv(file_name)
        df = df.loc[df['oneDimensionalCuts'] == cube_size]
        df = df.loc[df['minhashSize'] != 0]
        for size in time_cubes:
            minhash = df.loc[df['timeCubes'] == size]
            plt.plot(minhash['minhashSize'], minhash['FirstInCat']/k[j]*100, label=str(size))
    plt.legend()
    plt.savefig('results/' + file_name_simple + 'minhashCat-'+ str(cube_size) + '.png', dpi=800)
    plt.clf()
    i = 1
