Blackjack API
=============

An implementation of Blackjack using Java and Serverless Framework.

1. About 3,500 sloc of Java.
1. About 1,500 sloc of YAML.
1. About 500 sloc of Maven POM.

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

    $ mvn package -Dmaven.javadoc.skip -Dmaven.jxr.skip -Dmaven.test.skip \
                  -Dmaven.source.skip -pl :bj21-lambda -am

Deploy to AWS Lambda:

    $ sls deploy -s dev

Invoke a function in the Cloud:

    $ sls invoke --function=addTable --data '{"body":"{}"}' -s dev --log

Run DynamoDB test:

    $ mvn test -Dtest=org.ninthfloor.bj21.dynamodb.TablesTest -Dmaven.source.skip \
                -DfailIfNoTests=false -pl :bj21-dynamodb -am

The following will emulate invoking a function locally, but it will
fail because of the table environment variable won't be initialized as
a string by Serverless local.

    $ sls invoke local --function addTable --data '{"body":"{}"}' -s dev

You can run an integration test of a AWS Lambda function:

    $ mvn test -Dmdep.skip  -Dmaven.source.skip -DfailIfNoTests=false \
               -Dtest=org.ninthfloor.bj21.lambda.v0.AddTableTest -pl :bj21-lambda -am

Build the api docs for all the modules:

    $ mvn javadoc:aggregate

Build the source references for all the modules:

    $ mvn jxr:aggregate

To update dependencies to the next version::

    $ mvn versions:use-next-versions

To see if any pinned Maven plugins can be updated:

    $ mvn versions:display-plugin-updates
