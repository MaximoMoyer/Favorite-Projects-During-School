



	//making the scatter plot
	let svg2 = d3.select('div#graph2')
	    .append("svg")
        .attr("transform", `translate(${MAX_WIDTH/2}, ${-MAX_HEIGHT - 20})`)
	    .attr("width", MAX_WIDTH/2)
	    .attr("height", MAX_HEIGHT/2)
	    .append("g")
        .attr('transform', `translate(${margin.left - 10},0)`); 


    let tooltip = d3.select("div#graph2")  
        .append("div")
        .attr("class", "tooltip")
        .attr("width","500")
        .style("opacity", 0)

	d3.csv("/data/netflix.csv").then(function(data) {
	//only getting movies, you pass in attribute to remove
	data = cleanData(data,"TV Show")

	//creating the set of year
	let yearsDict = {}
	dataPoint = data.length
	for (i=0;i<dataPoint;i++){
	    data[i].duration = parseInt(data[i].duration)
	    if(data[i].release_year in yearsDict){
	    	yearsDict[data[i].release_year][0] += data[i].duration
	    	yearsDict[data[i].release_year][1] += 1
	    }
	    else{
	    	yearsDict[data[i].release_year] = [data[i].duration,1]
	    }
    }


	//turning data into array so it's easier to deal with
	var scatterData= []
    for (let key in yearsDict){
    	scatterData.push([key,Math.floor(yearsDict[key][0]/yearsDict[key][1]),yearsDict[key][1]])
    }
    //adding the rankings of all durations
    scatterData = scatterData.sort(comparator)
    var totalYears = scatterData.length
    for (i=0;i<totalYears;i++){
        scatterData[i].push(totalYears -i)
    }


    //min and max ranges
    let extentMean = d3.extent(scatterData, function(d) { return d[1]})

    let extentYear = d3.extent(scatterData, function(d) { return d[0]})


	let x2 = d3.scaleLinear()
	    .domain(extentYear)
	    .range([0, MAX_WIDTH/2 - margin.left - margin.right]);




	//x axis
	 svg2.append("g")
	    .attr("transform", `translate(0, ${MAX_HEIGHT/2 - margin.bottom - margin.top})`)
	    .call(d3.axisBottom(x2).tickFormat(d3.format("d")))


	//create a linear scale for the y axis
    let y2 = d3.scaleLinear()
        .domain([0,extentMean[1]])
        .range([MAX_HEIGHT/2 - margin.bottom - margin.top,margin.top]);


     svg2.append("g")
         .call(d3.axisLeft(y2));

    //getting a list of number of movies per year
    let movies = scatterData.map(function(d) { return d[2] });
    
    let color = d3.scaleOrdinal()
        .domain(movies)
        .range(d3.quantize(d3.interpolateHcl("rgb(150,20,20)", "rbg(250,20,20)"), movies.length));
    //Mouseover function to display the tooltip on hover
        let mouseover = function(d) {
            console.log(d)
            let color_span = `<span style="color: ${color(d[2])};">`;
            //d[0] is year, d[2] is number of movies in a given year, d[3] is ranking of duration with 1 longest
            let html =`<span> Netlix Movies from ${d[0]}: ${color_span} ${d[2]} </span><br>
                    <span> Average Duration: ${color_span}${d[1]} </span> <br>
                   <span> Duration Ranking: ${color_span} ${d[3]}/70 </span>`;

            // Show the tooltip and set the position relative to the event X and Y location
            tooltip.html(html)
                .style("left", `${MAX_WIDTH/2+455}px`)
                .style("top", `${40}px`)
                .style("box-shadow", `2px 2px 5px ${color(d[2])}`)
                .transition()
                .duration(200)
                .style("opacity", .9)
                .style("width","150px")
        };

        // Mouseout function to hide the tool on exit
        let mouseout = function(d) {
            // Set opacity back to 0 to hide
            tooltip.transition()
                .duration(200)
                .style("opacity", 0);
        };




        // Add chart title
        svg2.append("text")
            .attr("transform", `translate(${margin.left + 20},20)`)      
            .style("text-anchor", "middle")
            .style("font-size", 20)
            .text("Average Duration of Netflix Movies by Release Date")
            .style("text-decoration", "underline");

        // Creates a reference to all the scatterplot dots
        let dots = svg2.selectAll("dot").data(scatterData);

        dots.enter()
            .append("circle")
            .attr("cx", function (d) { return x2(d[0]) })
            .attr("cy", function (d) { return y2(d[1]) })
            .attr("r", 4)
            .attr("fill","rgb(200,50,0)")
            .on("mouseover", mouseover)
            .on("mouseout", mouseout);

        // Add x-axis label
        svg2.append("text")
            .attr("transform", `translate(${MAX_WIDTH/4 - margin.right - 40}, ${MAX_HEIGHT/2 - margin.top})`)       
            .style("text-anchor", "middle")
            .text("Year");

        // Add y-axis label
        svg2.append("text")
            .attr("transform", `translate(-90, ${MAX_HEIGHT/4 - margin.top +10})`)       
            .style("text-anchor", "middle")
            .text("Avg Duration (min)");

      

})



