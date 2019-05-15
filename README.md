# thesis-minhash

Code for evaluation of proposed techniques for creating descriptors suitable for for MinHash.

* Package ObjectData contains data holders for single descriptors.
- Package Loaders contains code for loading data from *.data files into data holders of descriptors.
- Class PermutationGenerator creates permutations that are saved into data files.
* Package MinhashCreators holds classes for transformation of raw data and descriptors into MinHash signatures. Also intermediate binary descriptors can be created.
- Package Evaluation holds class for Evaluation of kNN queries on all types of descriptors and also helper classes for evaluation of effectiveness and efficiency of search results. 
* Package Experiments contains scripts for experiments and helper classes for saving results into CSV files.
- Root folder contains python scripts for generating graphs from CSV files containing results. 