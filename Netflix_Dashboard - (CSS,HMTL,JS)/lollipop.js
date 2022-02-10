//lolipop graph
let svg3 = d3.select('div#graph3')
        .append("svg")
        .attr("transform", `translate(${MAX_WIDTH/2 - 70}, ${-MAX_HEIGHT - 20})`)
        .attr("width", MAX_WIDTH/2 +60)
        .attr("height", MAX_HEIGHT/2 + 20)
        .append("g")
        .attr('transform', `translate(${margin.left},0)`);

d3.csv("/data/netflix.csv").then(function(data) {
    //removing tv shows
    data = cleanData(data,"TV Show");
    
    let pairs = {}
    dataPoint = data.length
    for (i=0;i<dataPoint;i++){
        data[i].director = data[i].director.split(', ')
        data[i].cast = data[i].cast.split(', ')
        for (k=0;k<data[i].director.length;k++){
            for (j=0;j<data[i].cast.length;j++){
                if( data[i].director[k]!= '' && data[i].cast[j]!= ''){
                    if([data[i].director[k],data[i].cast[j]] in pairs){
                        pairs[[data[i].director[k],data[i].cast[j]]] += 1
                    }
                    else{
                        pairs[[data[i].director[k],data[i].cast[j]]] = 1
                    }
                }  
            }
        }
    }
    console.log(pairs)

    //keeping all pairs with value > 1, define as "top pairs"
    //87 for more than 3, 33 more than 4
    var graph3Data= []
    var values = [0,1,2,3,4]
    for (let key in pairs){
        if(pairs[key] > 4){
            graph3Data.push([key,pairs[key]])
            values.push(pairs[key])
        }
    }
    graph3Data = graph3Data.sort(comparator)
    console.log(graph3Data)
    let countRef = svg3.append("g");


    let x3 = d3.scaleLinear()
        .domain([0,d3.max(graph3Data, function(d){return d[1]})])
        .range([margin.left/8 + 70, MAX_WIDTH/2 - margin.left - margin.right + 60]);

    let y3 = d3.scaleBand()
        .domain(graph3Data.map(function(dat){return dat[0]}))
        .range([MAX_HEIGHT/2 - margin.bottom - margin.bottom,margin.top])
        .padding(0.1);  // Improves readability

    //add y-axis label
    svg3.append("g")
    	.attr("transform", `translate(${margin.left/8 + 70},0)`)
        .call(d3.axisLeft(y3).tickSize(10).tickPadding(10));

    svg3.append("g")
        .attr("transform", `translate(0, ${MAX_HEIGHT/2 - margin.bottom - margin.top})`)
        .call(d3.axisBottom(x3).tickValues(values).tickFormat(d3.format("d")))
    
    
    let lines3 = countRef.selectAll("line").data(graph3Data);


    //  Define color scale
    let color = d3.scaleOrdinal()
        .domain(graph3Data.map(function(d) { return d[1] }))
        .range(d3.quantize(d3.interpolateHcl("#66a0e2", "#81c2c3"), graph3Data.length));

    lines3.enter()
            .append("line")
            .merge(lines3)
            .transition()
            .duration(1000)
            .attr("x1",x3(0))
            .attr("y1",function(d) {return y3(d[0])+5.2})
            .attr("x2",function(d) {return x3(d[1])})
            .attr("y2",function(d) {return y3(d[0])+5.2})
            .style("stroke", "black");

    let circles = countRef.selectAll("circle").data(graph3Data);

    circles.enter()
            .append("circle")
            .transition()
            .duration(1000)
            .attr("r", "3")
            .attr("cx", function(d) { return x3(d[1])})
            .attr("cy", function(d) { return y3(d[0])+5.2})
            .style("fill", "red")
            .attr("stroke", "yellow")



    // Add x-axis label
        svg3.append("text")
            .attr("transform", `translate(${MAX_WIDTH/4  - margin.left + margin.right + 15}, ${MAX_HEIGHT/2 - margin.bottom})`)       
            .style("text-anchor", "middle")
            .text("Movies");

        // Add y-axis label
        svg3.append("text")
            .attr("transform", `translate(${MAX_WIDTH/4 - 2*margin.right - 290}, ${MAX_HEIGHT/4 -35})`)       
            .style("text-anchor", "middle")
            .text("Director,Actor");

        // Add chart title
        svg3.append("text")
            .attr("transform", `translate(${margin.left + margin.right - 7},${margin.top - 20})`)      
            .style("text-anchor", "middle")
            .style("font-size", 20)
            .text("Top 21 Most Common Director - Actor Pairings")
            .style("text-decoration", "underline");


})