import numpy as np
import pandas as pd
data_path = "C:/Users/Kolya/Documents/Fire/nfirs_data/2018/NFIRS_FIRES_2018_110119/"

pd.set_option('display.max_columns', 1000)

fi_df = pd.read_csv(data_path + "fireincident.txt", delimiter="^", engine='python')
fi_df["key"] = fi_df["STATE"].astype(str) + "_" + fi_df["FDID"].astype(str) + "_" + fi_df["INC_DATE"].astype(str) + "_" + fi_df["INC_NO"].astype(str) + "_" + fi_df["EXP_NO"].astype(str)
print(len(fi_df))
ia_df = pd.read_csv(data_path + "incidentaddress.txt", delimiter="^", engine='python')
ia_df["key"] = ia_df["STATE"].astype(str) + "_" + ia_df["FDID"].astype(str) + "_" + ia_df["INC_DATE"].astype(str) + "_" + ia_df["INC_NO"].astype(str) + "_" + ia_df["EXP_NO"].astype(str)



bi_df = pd.read_csv(data_path + "basicincident.txt", delimiter="^")
bi_df["key"] = bi_df["STATE"].astype(str) + "_" + bi_df["FDID"].astype(str) + "_" + bi_df["INC_DATE"].astype(str) + "_" + bi_df["INC_NO"].astype(str) + "_" + bi_df["EXP_NO"].astype(str)
bi_df.drop_duplicates(["key"], inplace=True)

fi_df = fi_df.merge(bi_df, how="left", left_on="key", right_on="key", suffixes=("x", "y"))
fi_df = fi_df.merge(ia_df, how="left", left_on="key", right_on="key", suffixes=("x", "z"))
#fi_df.to_csv(data_path + "fire_mash.csv")
print(fi_df.head())
fi_df["yyyy-mm-dd"] = fi_df["INC_DATEx"].apply(lambda x: str(x)[-4:] + "-" + (str(x)[:-6]).zfill(2) + "-" + str(x)[-6:-4])

fi_df.to_csv(data_path + "fire_mash.csv")
fire_short = fi_df[["STATEx", "FDIDx", "INC_DATEx", "INC_NOx", "EXP_NOx", "key", "ALARM", "ZIP5"]]

fire_short.to_csv(data_path + "fire_short.csv")
