import numpy as np
import pandas as pd

pd.set_option('display.max_columns', 1000)

batch_path = "C:/Users/Kolya/Documents/Fire/nfirs_data/batch_files/"

from os import listdir
from os.path import isfile, join
onlyfiles = [f for f in listdir(batch_path) if isfile(join(batch_path, f))]
dfs = [pd.read_csv(batch_path + f) for f in onlyfiles]
for (x, df) in zip(onlyfiles, dfs):
    df["batch"] = x[11:-4]
combine_df = pd.concat(dfs)
combine_df.sort_values("Unnamed: 0", inplace=True)
combine_df.to_csv("C:/Users/Kolya/Documents/Fire/nfirs_data/comb_file.csv")
print(combine_df)
