# Running abixen-platform using docker-compose
This config allows you to run **ALL** abixen-platform components.

# Logging
All containers from abixen-platform send logs to Elasticsearch via Logstash.
You can access Kibana interface using its exposed port on [http://localhost:5601](http://localhost:5601).


## Elasticsearch workaround
If you encounter an error similar to this one (on Linux platform)

```
elasticsearch_1 | ERROR: bootstrap checks failed
elasticsearch_1 | max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
```

then you need to run the following command:

```
sudo sysctl -w vm.max_map_count=262144
```
