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

Invoke function:

    $ sls invoke --function=addTable -s dev --log
