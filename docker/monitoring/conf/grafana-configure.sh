curl -v  http://localhost:3000/api/datasources -d @datasource.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='
curl -v  http://localhost:3000/api/dashboards/db -d @dash.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='

