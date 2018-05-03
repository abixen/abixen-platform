#!/usr/bin/env bash

rmdir db /s /q
java -cp h2-1.4.182.jar org.h2.tools.RunScript -user "sa" -password "" -url jdbc:h2:./db/business-intelligence-demo-data;DB_CLOSE_DELAY=-1 -script create.sql
java -cp h2-1.4.182.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 8000