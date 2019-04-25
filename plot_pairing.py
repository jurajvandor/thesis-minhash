import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import sys

k = [1,5,10,20]
avgs = ['avg1.csv', 'avg5.csv', 'avg10.csv', 'avg20.csv']
names = ['k = 1', 'k = 5', 'k = 10', 'k = 20']

def plt_four():
    i = 1
    for j in range(4):
        plt.subplot(2, 2, i)
        i += 1
        plt.title(names[j])
        plt.tight_layout()
        plt.ylabel('presnosť (%)')
        plt.xlabel('veľkosť MinHashu')
        plt.ylim(70, 85)
        file_name = sys.argv[1]
        file_name_simple = file_name.replace('/', "-") + sys.argv[2]
        df = pd.read_csv(file_name + 'and/' + sys.argv[2] + '/' + avgs[j])
        plt.plot(df['minhashSize'], df['SameItems']/k[j]*100, label='AND')
        df = pd.read_csv(file_name + 'or/' + sys.argv[2] + '/' + avgs[j])
        plt.plot(df['minhashSize'], df['SameItems']/k[j]*100, label='OR')
        if j == 3:
            plt.legend()
    plt.savefig('results/' + file_name_simple + '.png', dpi=800)

def plt_one():
    file_name = sys.argv[1]
    file_name_simple = file_name.replace('/', "-")
    for j in range(4):
        plt.subplot(2,2,1)
        plt.title('ImageNeural + AND')
        plt.tight_layout()
        plt.ylabel('presnosť (%)')
        plt.xlabel('veľkosť MinHashu')
        plt.ylim(20, 50)
        df = pd.read_csv(file_name + 'and/' + 'img' + '/' + avgs[j])
        plt.plot(df['minhashSize'], df['SameItems']/k[j]*100, label=names[j])
    for j in range(4):
        plt.subplot(2,2,2)
        plt.title('ImageNeural + OR')
        plt.tight_layout()
        plt.ylabel('presnosť (%)')
        plt.xlabel('veľkosť MinHashu')
        plt.ylim(20, 50)
        df = pd.read_csv(file_name + 'or/' + 'img' + '/' + avgs[j])
        plt.plot(df['minhashSize'], df['SameItems']/k[j]*100, label=names[j])
    for j in range(4):
        plt.subplot(2,2,3)
        plt.title('MotionNeural + AND')
        plt.tight_layout()
        plt.ylabel('presnosť (%)')
        plt.xlabel('veľkosť MinHashu')
        plt.ylim(70, 85)
        df = pd.read_csv(file_name + 'and/' + 'motion' + '/' + avgs[j])
        plt.plot(df['minhashSize'], df['SameItems']/k[j]*100, label=names[j])
    for j in range(4):
        plt.subplot(2,2,4)
        plt.title('MotionNeural + OR')
        plt.tight_layout()
        plt.ylabel('presnosť (%)')
        plt.xlabel('veľkosť MinHashu')
        plt.ylim(70, 85)
        df = pd.read_csv(file_name + 'or/' + 'motion' + '/' + avgs[j])
        plt.plot(df['minhashSize'], df['SameItems']/k[j]*100, label=names[j])
    plt.legend()
    plt.savefig('results/' + file_name_simple + 'single.png', dpi=800)

plt_one()
