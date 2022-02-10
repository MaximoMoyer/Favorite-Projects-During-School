// Add your JavaScript code here
const MAX_WIDTH = Math.max(1080, window.innerWidth);
const MAX_HEIGHT = 720;
const margin = {top: 40, right: 100, bottom: 40, left: 175};

// Assumes the same graph width, height dimensions as the example dashboard. Feel free to change these if you'd like
let graph_1_width = (MAX_WIDTH / 2) - 10, graph_1_height = 250;
let graph_2_width = (MAX_WIDTH / 2) - 10, graph_2_height = 275;
let graph_3_width = MAX_WIDTH / 2, graph_3_height = 575;


//bar Graph by genre
let svg = d3.select('div#graph1')
    .append("svg")
    .attr("width", MAX_WIDTH/2)
    .attr("height", MAX_HEIGHT)
    .append("g")
    .attr('transform', `translate(${margin.left},${margin.top})`); 

//Create a linear scale for the x axis (number of occurrences for each genre)
let x = d3.scaleLinear()
    .range([0, MAX_WIDTH/2 - 250]);

//Set up reference to count SVG group
let countRef = svg.append("g");

//scale for y axis
let y = d3.scaleBand()
    .range([MAX_HEIGHT - `${margin.top}` - `${margin.bottom}`,0])
    .padding(0.1);

//y axis label
let y_axis_label = svg.append("g");


svg.append("text")
    .attr("transform", `translate(${(MAX_WIDTH/2 - margin.left- margin.right)/2-1},${MAX_HEIGHT - margin.bottom - 20})`)       // HINT: Place this at the bottom middle edge of the graph
    .style("text-anchor", "middle")
    .text("Title Count");

//Add y-axis label
let y_axis_text = svg.append("text")
    .attr("transform", `translate(${-margin.left +40},${(MAX_HEIGHT - margin.top - margin.bottom)/2})`)       // HINT: Place this at the center left edge of the graph
    .style("text-anchor", "middle");


let title = svg.append("text")
    .attr("transform", `translate(${(MAX_WIDTH/2 - margin.left - margin.right)/2},-15)`)       // HINT: Place this at the top middle edge of the graph
    .style("text-anchor", "middle")
    .style("font-size", 20)
    .attr("id","titleG1")
    .style("text-decoration", "underline")
    




/**
 * Sets the data on the barplot using the provided index of valid data sources and an attribute
 * to use for comparison
 */
function setData(type) {
    d3.csv("/data/netflix.csv").then(function(data) {
   	//Only including TV or movie values
    console.log(data)
   	if (type !="TV Show and Movie"){
   			if(type =="TV Show"){
                //removing movies
   				data = cleanData(data,"Movie");
   			}
   			if(type =="Movie"){
                //removing tv shows
   				data = cleanData(data,"TV Show");
   			}
    	}
    
    //creating the set of genres
    let genreSet = new Set()
    dataPoint = data.length
    for (i=0;i<dataPoint;i++){
        data[i].listed_in = data[i].listed_in.split(', ')
        for(k=0;k < data[i].listed_in.length;k++){
        	//removing meanginless genre titles
        	if (data[i].listed_in[k] =='TV Shows' || data[i].listed_in[k] =='Movies'){
        		data[i].listed_in.splice(k,1)
        		//if one movie/TV show only had a meaningless genre, changed genre name to undefined
        		if(data[i].listed_in.length == 0){
        			data[i].listed_in = ['Undefined']
        		}
    		}
			genreSet.add(data[i].listed_in[k])
        	}
        }
    console.log(data)

    //note anime series is TV Shows, Anime features is movies
    // docuseries tv., documetaries movie

    //removing the meaningless attribute from the set
    if (type !="TV Show and Movie"){
    	type.concat('s')
    	genreSet.delete(type)
    	}
	
	else{
		genreSet.delete('TV Shows')
		genreSet.delete('Movies')
	}
    
    genreSet.add('Undefined')

    //getting the counts for each genre

    //initializng dictionary
    var dict = {}
    	//initializing a key for each genre with value 0
    	
	    for (let item of genreSet){
	    	dict[item] = 0
	    }
	    
	    //adding one to the dictionary for each instance of a count
	   	for (i=0;i<dataPoint;i++){
	   		
	   		for(k=0;k < data[i].listed_in.length;k++){
	   		dict[data[i].listed_in[k]] ++
	   		}
	    }

	   	

        //changing the dictionary to an array so it can be data-bounded more easily
        

        var graphData= []
        for (let item of genreSet){
        	graphData.push([item,dict[item]])
        }
        graphData = graphData.sort(comparator)
        
        //getting just values to pass into boxplot later
        var values = []
        var total = 0
        for (i=0; i < graphData.length;i++){
        	values.push(graphData[i][1])
            total += graphData[i][1]
        }
        
        //setting min and max values for each dataset
        maxVal = graphData[graphData.length -1][1]
        minVal = graphData[0][1]
        
        // Update the x axis domain with the max count of the provided data
        x.domain([0,maxVal]);

        //setting the y domain to each genre of the provided data
        y.domain(graphData.map(function(dat){return dat[0]}));

      
        y_axis_label.call(d3.axisLeft(y).tickSize(0).tickPadding(10));

        // /*
        //     This next line does the following:
        //         1. Select all desired elements in the DOM
        //         2. Count and parse the data values
        //         3. Create new, data-bound elements for each data value
        //  */

        let bars = svg.selectAll("rect").data(graphData);
        //removing the bars from the previous step
        bars.exit().remove();
        // // TODO: Render the bar elements on the DOM
        // /*
        //     This next section of code does the following:
        //         1. Take each selection and append a desired element in the DOM
        //         2. Merge bars with previously rendered elements
        //         3. For each data point, apply styling attributes to each element

        //     Remember to use the attr parameter to get the desired attribute for each data point
        //     when rendering.
        //  */
        genres = graphData.length
        
        let color = d3.scaleOrdinal()
        .domain(graphData.map(function(dat){return dat[0]}))
        .range(d3.quantize(d3.interpolateHcl("rgb(150,20,0)", "rgb(255,70,70)"),genres));


        bars.enter()
            .append("rect")
            .merge(bars)
            .transition()
            .duration(1000)
            .attr("fill", function(d) {return color(d[0])})
            .attr("x", x(0))
            .attr("y", function(d){return y(d[0])})               // HINT: Use function(d) { return ...; } to apply styles based on the data point
            .attr("width", function(d){return x(d[1])})
            .attr("height",  y.bandwidth());        // HINT: y.bandwidth() makes a reasonable display height

        // /*
        //     In lieu of x-axis labels, we are going to display the count of the artist next to its bar on the
        //     bar plot. We will be creating these in the same manner as the bars.
        //  */
        let counts = countRef.selectAll("text").data(graphData);
        //removing the counts from the previous step
        counts.exit().remove();

        // //Render the text elements on the DOM
        counts.enter()
            .append("text")
            .merge(counts)
            .transition()
            .duration(1000)
            .attr("x", function(d) {return x(d[1]) + 10})       // HINT: Add a small offset to the right edge of the bar, found by x(d.count)
            .attr("y", function(d) {return y(d[0]) + 10})       // HINT: Add a small offset to the top edge of the bar, found by y(d.artist)
            .style("text-anchor", "start")
            .text(function(d) {return d[1]});           // HINT: Get the count of the artist

        y_axis_text.text("Genres");
        title.text('Netflix ' + type + ' Title Count by Genre');



        //removing previous boxplot
        d3.select('div#graph1').selectAll('line').remove()
        //creating the boxplot

        if(type == 'TV Show'){
        	var lineOffset = 0
        }
        else if (type == 'Movie'){
        	var lineOffset = 0
        }
        else{
        	var lineOffset = 4
        }

        //orginal horizontal line
        svg.append("line")
			.attr("x1",x(minVal))
			.attr("y1",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset)
			.attr("x2",x(maxVal))
			.attr("y2",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset)
			.style("stroke", "black")
			.style("fill","transparent")
			.attr("class","boxplot")

		//vertial line left
		svg.append("line")
			.attr("x1",x(minVal))
			.attr("y1",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset - 30)
			.attr("x2",x(minVal))
			.attr("y2",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset  + 30)
			.style("stroke", "black")
			.attr("class","boxplot")

		//vertical line right
		svg.append("line")
			.attr("x1",x(maxVal))
			.attr("y1",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset - 30)
			.attr("x2",x(maxVal))
			.attr("y2",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset + 30)
			.style("stroke", "black")
			.attr("class","boxplot")

		//geting quantiles
		let q1 = d3.quantile(values,0.25)
		let q2 = d3.quantile(values,0.5)
		let q3 = d3.quantile(values,.75)

		//vertical line middle
		svg.append("line")
			.attr("x1",x(q2))
			.attr("y1",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset - 30)
			.attr("x2",x(q2))
			.attr("y2",(MAX_HEIGHT - margin.top - margin.bottom)/2 + lineOffset + 30)
			.style("stroke", "black")
			.attr("class","boxplot")

		//box
		svg.append("rect")
			.transition()
            .attr("fill", "rgb(0,150,100,.4)")
            .attr("x", x(q1))
            .attr("y", (MAX_HEIGHT - margin.top - margin.bottom)/2 +lineOffset -30)
            .attr("width", x(q3) - x(q1))
            .attr("height", 60); 




        // // Remove elements not in use if fewer groups in new dataset
        
        

    });

}

let comparator = function(a,b) {
    // TODO: sort and return the given data with the comparator (extracting the desired number of examples)
    if (b[1] > a[1]){
        return -1
    }
    if (b[1] == a[1]){
        return 0
    }
    if (a[1] > b[1]){
        return 1
    }


}

//pass in attribute to remove
function cleanData(data, attr) {
    for (i = data.length-1; i > -1 ; i = i-1){
    	if(data[i].type == attr){
    		data.splice(i,1)
    	}
    }
    return data
}

setData("TV Show and Movie");






