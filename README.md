Blackjack API
=============

An implementation of Blackjack using Java and Serverless.

Generate sources:

    $ mvn generate-sources

Run tests:

    $ mvn test -Dmaven.source.skip

Run tests for one module:

    $ mvn test -pl :bj21-lambda -am

Run a single test:

    $ mvn test -DfailIfNoTests=false -Dtest=testname -pl :bj21-lambda -am

Show stack trace on failure:

    $ mvn test -DtrimStackTrace=false

Build artifact for AWS Lambda:

    $ mvn package -Dmaven.javadoc.skip -Dmaven.test.skip -pl :bj21-lambda -am

Deploy to AWS Lambda:

    $ sls deploy -s dev

Invoke a function in the Cloud:

    $ sls invoke --function=addTable --data '{"body":"{}"}' -s dev --log

Run DynamoDB test:

    $ mvn test -Dtest=org.ninthfloor.bj21.dynamodb.TablesTest -pl :bj21-dynamodb -am -Dmaven.source.skip -DfailIfNoTests=false

The following will emulate invoking a function locally, but it will
fail because of the table environment variable won't be initialized as
a string by Serverless local.

    $ sls invoke local --function addTable --data '{"body":"{}"}' -s dev

You can run an integration test of a AWS Lambda function:

    $ env TABLES_TABLE_NAME=Tables  mvn test -pl :bj21-lambda -am -Dmaven.source.skip -DfailIfNoTests=false -Dtest=org.ninthfloor.bj21.lambda.v0.AddTableTest
