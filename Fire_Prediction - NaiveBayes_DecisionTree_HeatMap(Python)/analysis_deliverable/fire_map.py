import numpy as np
import pandas as pd

fire_stats = pd.read_csv("C:/Users/Kolya/Documents/Fire/nfirs_data/fire_short.csv")
#print(fire_stats)

fg = fire_stats.groupby("ZIP5").count()
print(fg.sort_values())
#fg[["batch"]].to_csv("C:/Users/Kolya/Documents/Fire/nfirs_data/zip_counts.csv")
