#!/bin/sh

IFS= #prevent word splitting

host=$1
while true;
do
    sleep 5
    if curl -s -I http://$host:7474 | grep -q "200 OK"
    then
        echo "Neo4j Ready - Adding constraints"
        # Create constraint on conversation ids to avoid multiple insertion when starting containers multiple times
        curl -X POST -H 'Content-type: application/json' http://neo4j:coney@$host:7474/db/data/transaction/commit -d '{"statements": [{"statement": "CREATE CONSTRAINT ON (c:Conversation) ASSERT c.conv_id IS UNIQUE;"}]}'
        echo "Neo4j Ready - Add data"
	for data in api/data/*.txt
	do
		conv_id=`sed -e 's/.*\"conversationId\":\"\([^"]*\)\".*/\1/' < "$data"`
		conv_title=`sed -e 's/.*\"title\":\"\([^"]*\)\".*/\1/' < "$data"`
		conv_data=`basename $data`

		curl -X POST -H 'Content-type: application/json' http://neo4j:coney@$host:7474/db/data/transaction/commit -d "{\"statements\": [{\"statement\": \"MERGE (c:Conversation {conv_id: \\\"$conv_id\\\", conv_title: \\\"$conv_title\\\", conv_type: \\\"\\\", json_url: \\\"/opt/data/${conv_data}\\\", lang: \\\"en\\\", accessLevel: 1, status: \\\"saved\\\"});\"}]}"
	done
        break
    else
        echo "Neo4j Not Ready"
        continue
    fi
done 
