Blackjack API
=============

An implementation of Blackjack using Java and Serverless.

Generate sources:

    $ mvn generate-sources

Run tests:

    $ mvn test

Build artifact for AWS Lambda:

    $ mvn package -Dmaven.javadoc.skip -rf :bj21-lambda

Deploy to AWS Lambda

    $ sls deploy -s dev
