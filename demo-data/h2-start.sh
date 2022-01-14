#!/bin/sh

rm -r db
java -cp h2-2.0.206.jar org.h2.tools.RunScript -user "sa" -password "" -url "jdbc:h2:./db/business-intelligence-demo-data;DB_CLOSE_DELAY=-1" -script create.sql
java -cp h2-2.0.206.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 8000