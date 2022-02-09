# Final Project
This was a final project for my data science class

- #### Reports ####
  - [Tech Report](data_report/README.md)

## Final Deliverable ##

- #### Poster ####
  [Poster](final_deliverable/poster/Poster.pdf)

- #### Code ####
  [Code](final_deliverable/code/)

- #### Visualizations ####
  [Visualizations](final_deliverable/visualizations)
  - [Decision Tree Max-Depth 3 Visualization](final_deliverable/visualizations/depth_3.png)
  - [Decision Tree Max-Depth 6 Results](final_deliverable/visualizations/Decision_Tree_Test_Table_Heat.png)
  - [Naive Bayes Results](final_deliverable/visualizations/Naive_Bayes_Test_Table_Heat.png)
  - [Fire Occurence Heatmap](final_deliverable/visualizations/fire_map.jpg)

  Additional Notes:

  - Our Decision Tree and Naive Bayes results were found by running our models/tests and copying and pasting that output into Excel (where we formatted the tables).
  - We visualized the decision tree using [sklearn.tree.plot_tree](https://scikit-learn.org/stable/modules/generated/sklearn.tree.plot_tree.html) and [Matplotlib](https://matplotlib.org/).
  - Note that on our poster, to make the visualization fit, we had to take screenshots of the individual nodes on the tree and paste those into our poster in order to fit our max-depth 3 visualization (without warping the visualization or having too much clutter). Additionaly, note that we also have visualizations for decision trees with increased depth in our analysis deliverable. However, the results of these are harder to interpret as the tree is much, much larger.
  - The heatmap was made in ArcMap, where we joined our table of fires by zip code, a zip code shapefile, and the population of each zipcode on the key of zip code.

