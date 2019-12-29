#!/bin/bash
source /etc/profile
mvn clean install -Dmaven.test.skip=true -f $1
