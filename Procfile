web: java -Dserver.port=$PORT $JAVA_OPTS -jar app-0.0.1-SNAPSHOT.jar
migrate: java -jar dependency/liquibase.jar --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml --url=$JDBC_DATABASE_URL --classpath=dependency/postgres.jar update