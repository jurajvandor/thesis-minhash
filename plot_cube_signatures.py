import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
names = ['k = 1', 'k = 5', 'k = 10', 'k = 20']
k = [1,5,10,20]

cube_sizes = [5,10,15,20,25,30,35]
time_cubes = [1,2,3,4,5,6,7,8,9,10]

i = 1
for j in range(4):
    plt.subplot(2, 2, i)
    i += 1
    plt.title(names[j])
    plt.tight_layout()
    plt.ylabel('presnosť (%)')
    plt.xlabel('počet segmentov v jednej dimenzíi')
    plt.ylim(25, 85)
    file_name = sys.argv[1]
    file_name_simple = file_name.replace('/', "-")
    file_name += avgs[j]
    df = pd.read_csv(file_name)
    df = df.loc[df['minhashSize'] == 0]
    for size in time_cubes:
        sig = df.loc[df['timeCubes'] == size]
        plt.plot(sig['oneDimensionalCuts'], sig['FirstInCat']/k[j]*100, label=str(size))
    if j == 3:
        plt.legend()
plt.savefig('results/' + file_name_simple + 'signatures-cat' + '.png', dpi=800)
