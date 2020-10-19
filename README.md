Blackjack client
==================

Example using the new HTTP client (java.net.http.HttpClient) that
premiered in Java 11.

Run tests:

    $ mvn test

Run a single test:

    $ mvn test -DfailIfNoTests=false -Dtest=HttpTest

Show stack trace on failure:

    $ mvn test -DtrimStackTrace=false

Build artifact for AWS Lambda:

    $ mvn package -Dmaven.javadoc.skip -Dmaven.test.skip

Run the application:

    $ mvn exec:java -Dexec.mainClass=org.ninthfloor.bj21.client.Http

If you want to run with assertions enabled:

    $ env MAVEN_OPTS=-ea mvn exec:java -Dexec.mainClass=org.ninthfloor.bj21.client.Http

Build the api docs for all the modules:

    $ mvn javadoc:aggregate

Build the source references for all the modules:

    $ mvn jxr:aggregate

To update dependencies to the next version::

    $ mvn versions:use-next-versions

To see if any pinned Maven plugins can be updated:

    $ mvn versions:display-plugin-updates

## Resources

- <https://docs.oracle.com/en/java/javase/11/>
- <https://docs.oracle.com/en/java/javase/11/docs/api/index.html>
- <http://openjdk.java.net/groups/net/httpclient/>
- <https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html>
- <https://maven.apache.org/>
- <https://junit.org/junit4/>
- <https://docs.oracle.com/javase/7/docs/technotes/guides/language/assert.html>
- <https://docs.oracle.com/cd/E19683-01/806-7930/6jgp65ikl/index.html>
