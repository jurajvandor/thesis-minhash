import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

avgs = ['1.csv', '5.csv', '10.csv', '20.csv']
cube_sizes = [10,20,30]
time_cubes = [1,2,3,4,5]
for avg in avgs:

    file_name = sys.argv[1] + avg

    file_name_simple = file_name.replace('/', "-")

    df = pd.read_csv(file_name)
    for cube_size in cube_sizes:
        plt.ylabel('items in queried category')
        plt.xlabel('minhash size')
        df2 = df.loc[df['oneDimensionalCuts'] == cube_size]
        plt.plot(df2['minhashSize'], df2['SecondInCat'], label='reference')
        for time_cube in time_cubes:
            table = df2.loc[df['timeCubes'] == time_cube]
            plt.plot(table['minhashSize'], table['FirstInCat'], label=str(time_cube))
        plt.legend()
        plt.savefig('results/cube/cat/' + file_name_simple + '-' + str(cube_size) +'.png')
        plt.clf()
