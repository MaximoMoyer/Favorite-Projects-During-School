# Final Project Summary
This was a final project for my data science class. We requested FEMA's NFIRS (National Fire Incident Reporting System) incident report data. We joined this data (details in data report below) with the virtualcrossing weather API based on zip code and day, to get the weather in a given location on the given day where there was a fire. We then used this joint data to see if we could predict which of the most common 3 fire types ("Vehicle", "Structural", "Natural vegetation") would ocurr on a given day where there was a fire. We thought the application of this knoweldge might help fire fighters more quickly prepare for different fire types depending on a day's weather.  We used a Naive Bayes model, a Decision Tree, a heat map, and seasonal bar chart in our investigation.  

- A more in depth, 1 page discussion of our outcomes can be found here: [Outcomes](final_deliverable/poster/DS_Final_Project_one_page_summary.pdf)

- The folder with our code (complete with a readme including design decisions and run instructions) can be found here: [Code Repo](final_deliverable/code). 

- A report on how we collected and joined our data, alongside its shortcomings can be found here: [Data Report](data_report/README.md)

- A presentation of our poster (below) can be found here: [Presentation](https://youtu.be/blUu6flG1EI)

## Visual Deliverables ##

- #### Poster ####
  [Poster](final_deliverable/poster/Poster.pdf)

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

