# Final Project
This is the final project's master repo! Please use the following shortcut links to access the different components of the project.

## Data Deliverable ##

- #### Data Spec ####
  [Data spec](data_deliverable/data/)

- #### Reports ####
  - [Tech Report](data_deliverable/reports/tech_report/)
  - [Socio-historical Context and Impact Report](data_deliverable/reports/social_impact_report)


# Analysis Deliverable
This is the analysis deliverable's master directory! Please use the following shortcut links to access the different components of this deliverable.

### Reports ###
[Tech Report](analysis_deliverable/tech_report/)

### Analysis Code ###
- [Code](analysis_deliverable/analysis_code/)

### Visualizations ###

[Visualizations](analysis_deliverable/visualizations)
- [Decision Tree Depth 6 Train Confusion Matrix](analysis_deliverable/visualizations/decision_tree_train_depth_6.png)
- [Decision Tree Depth 6 Test Confusion Matrix](analysis_deliverable/visualizations/decision_tree_test_depth_6.png)
- [Decision Tree Depth 5 Visualization](analysis_deliverable/visualizations/depth_5_tree.png)
- [Decision Tree Depth 3 Visualization](analysis_deliverable/visualizations/depth_3_tree.png)
- [Fire Occurence Heatmap](analysis_deliverable/visualizations/fire_map.jpg)
- [Naive Bayes Train Confusion Matrix](analysis_deliverable/visualizations/naive_bayes_train.png)
- [Naive Bayes Test Confusion Matrix](analysis_deliverable/visualizations/naive_bayes_test.png)
- [Heat Map Python Groubpy](analysis_deliverable/analysis_code/fire_map.py)
- [Heat Map ArcMap](analysis_deliverable/analysis_code/fire_map.mxd)

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

