function renderBarGraph(chartDivId, data, startRecord, rowsPerPage) {

  var lastRecordIndex = Math.min(startRecord + rowsPerPage, data.length);
  var dataset = data.slice(startRecord, lastRecordIndex);

  if (dataset.length < 2) {
    return; // don't render for a single row
  }

  var datasetById = [];
  for (var i = 0; i < dataset.length; i++) {
    datasetById[dataset[i].id] = dataset[i];
  }

  var chartDiv = document.getElementById(chartDivId);
  var boundingClientRect = chartDiv.getBoundingClientRect();

  var height = 120;
  var width = boundingClientRect.width;

  var margin = {top: 20, right: 0, bottom: 5, left: 40};
  var gWidth = width - margin.left - margin.right;
  var gHeight = height - margin.top - margin.bottom;

  var x = d3.scale.ordinal()
    .rangeRoundBands([0, gWidth], 0.2)
    .domain(dataset.map(function (d) {
      return d.id;
    }));

  var y = d3.scale.linear()
    .range([gHeight, 0])
    .domain([0, d3.max(dataset, function (d) {
      return d.duration;
    })]);

  var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(5);

  var tip = d3.tip()
    .attr('class', 'd3-tip')
    .offset([-10, 0])
    .html(function (d) {
      return "<strong>Duration:</strong> <span style='color:orangered'>" + d.duration + " ms</span>";
    });

  var svg = d3.select("#" + chartDivId)
    .append("svg")
    .attr("width", width)
    .attr("height", height)
    .attr("viewBox", (0 + " " + 0 + " " + width + " " + height))
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

  svg.call(tip);

  svg.append("g")
    .attr("class", "y axis")
    .call(yAxis);

  svg.selectAll(".bar")
    .data(dataset)
    .enter().append("rect")
    .attr("class", "bar")
    .attr("id", function (d) {
      return 'mse_' + d.id + '_bar';
    })
    .attr("x", function (d) {
      return x(d.id);
    })
    .attr("width", x.rangeBand())
    .attr("y", function (d) {
      return y(d.duration);
    })
    .attr("height", function (d) {
      return gHeight - y(d.duration);
    })
    .on('mouseover', function (d) {
      tip.show(d);
      $('#mse_' + d.id).addClass('highlight');

    })
    .on('mouseout', function (d) {
      tip.hide(d);
      $('#mse_' + d.id).removeClass('highlight');
    });

  // note that I use native javascript to add/remove classes as JQuery does not handle SVG elements
  $('.mse').hover(function (e) {
    var rowId = e.delegateTarget.id;
    var bar = document.getElementById(rowId + '_bar');

    bar.classList.add('highlight');
    e.delegateTarget.classList.add('highlight');

    tip.show(datasetById[rowId.substr(4, rowId.length)], bar);

  }, function (e) {
    var rowId = e.delegateTarget.id;
    var bar = document.getElementById(rowId + '_bar');

    bar.classList.remove('highlight');
    e.delegateTarget.classList.remove('highlight');

    tip.hide(bar);
  });
}