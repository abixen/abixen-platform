sleep 20

curl -v  http://grafana:3000/api/datasources -d @/conf/datasource.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='
curl -v  http://grafana:3000/api/dashboards/db -d @/conf/dash.json --header 'Content-Type: application/json' --header 'Authorization: Basic YWRtaW46YWRtaW4='

