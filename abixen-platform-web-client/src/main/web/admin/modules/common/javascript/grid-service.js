var GridFilter = function () {

    this.availableOperators = {eq: '=', not: '!', like: '~', and: '&', or: '|'};
    this.globalOperator = this.availableOperators.and;


    this.getJsonCriteria = function (grid) {
        console.log('getJsonCriteria grid: ', grid);

        var stringExpression = '';

        var filterSize = 0;
        for (var i = 0; i < grid.columnDefs.length; i++) {
            if (grid.columnDefs[i].filter) {
                console.log('filter [' + i + ']: ', grid.columnDefs[i].filter);

                console.log(grid.columnDefs[i].field + ', ' + grid.columnDefs[i].filter.term);

                if(filterSize === 0){
                    stringExpression += '(';
                }else{
                    stringExpression += this.globalOperator + '(';
                }
                stringExpression += grid.columnDefs[i].filter.term;
                stringExpression += ')'

                filterSize++;
            }

        }

        console.log('stringExpression:', stringExpression);
    };
};

GridFilter.prototype.$get = function () {
    return this;
};