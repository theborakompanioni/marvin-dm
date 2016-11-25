'use strict';

angular.module('repository')
    .controller('RepositoryCtrl', ['$scope', function ($scope) {
        $scope.ModuleTest = "Project Dependency Summary";

        $scope.jsonMock = {
            "statusBadgeUrl": "http://localhost:8080/theborakompanioni/vertx-rx-springboot.svg",
            "author": "theborakompanioni",
            "projectName": "vertx-rx-springboot",
            "description": "RxVertx SpringBoot",
            "pom": "https://github.com/theborakompanioni/vertx-rx-springboot/blob/master/pom.xml",
            "dependencies" : {
                "org.mockito:mockito-all" : {
                    "required" : "1.10.19",
                    "latest" : "2.0.2-beta"
                }
            },
            "dependencyManagement" : {
                "antlr:antlr" : {
                    "required" : "2.7.7",
                    "latest" : "20030911"
                },
                "com.atomikos:transactions-jdbc" : {
                    "required" : "3.9.3",
                    "latest" : "4.0.4"
                },
                "com.atomikos:transactions-jms" : {
                    "required" : "3.9.3",
                    "latest" : "4.0.4"
                },
                "com.atomikos:transactions-jta" : {
                    "required" : "3.9.3",
                    "latest" : "4.0.4"
                },
                "com.couchbase.client:java-client" : {
                    "required" : "2.2.8",
                    "latest" : "2.3.5"
                },
                "com.datastax.cassandra:cassandra-driver-core" : {
                    "required" : "2.1.9",
                    "latest" : "3.1.2"
                },
                "com.datastax.cassandra:cassandra-driver-dse" : {
                    "required" : "2.1.9",
                    "latest" : "3.0.0-rc1"
                },
                "com.datastax.cassandra:cassandra-driver-mapping" : {
                    "required" : "2.1.9",
                    "latest" : "3.1.2"
                },
                "com.fasterxml.jackson.core:jackson-annotations" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.core:jackson-core" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.core:jackson-databind" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.dataformat:jackson-dataformat-cbor" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.dataformat:jackson-dataformat-csv" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.dataformat:jackson-dataformat-smile" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.dataformat:jackson-dataformat-xml" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-guava" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-hibernate4" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-hibernate5" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-jaxrs" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-jdk8" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-joda" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-json-org" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.jaxrs:jackson-jaxrs-base" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.module:jackson-module-jaxb-annotations" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.module:jackson-module-kotlin" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.fasterxml.jackson.module:jackson-module-parameter-names" : {
                    "required" : "2.8.4",
                    "latest" : "2.8.5"
                },
                "com.github.ben-manes.caffeine:caffeine" : {
                    "required" : "2.3.4",
                    "latest" : "2.3.5"
                },
                "com.github.mxab.thymeleaf.extras:thymeleaf-extras-data-attribute" : {
                    "required" : "1.3",
                    "latest" : "2.0.1"
                },
                "com.google.appengine:appengine-api-1.0-sdk" : {
                    "required" : "1.9.44",
                    "latest" : "1.9.46"
                },
                "com.google.code.gson:gson" : {
                    "required" : "2.7",
                    "latest" : "2.8.0"
                },
                "com.hazelcast:hazelcast" : {
                    "required" : "3.6.6",
                    "latest" : "3.7.3"
                },
                "com.hazelcast:hazelcast-hibernate4" : {
                    "required" : "3.6.6",
                    "latest" : "3.7"
                },
                "com.hazelcast:hazelcast-spring" : {
                    "required" : "3.6.6",
                    "latest" : "3.7.3"
                },
                "com.samskivert:jmustache" : {
                    "required" : "1.12",
                    "latest" : "1.13"
                },
                "com.sendgrid:sendgrid-java" : {
                    "required" : "2.2.2",
                    "latest" : "3.1.0"
                },
                "com.zaxxer:HikariCP" : {
                    "required" : "2.4.7",
                    "latest" : "2.5.1"
                },
                "commons-collections:commons-collections" : {
                    "required" : "3.2.2",
                    "latest" : "20040616"
                },
                "dom4j:dom4j" : {
                    "required" : "1.6.1",
                    "latest" : "20040902.021138"
                },
                "io.projectreactor:reactor-core" : {
                    "required" : "2.0.8.RELEASE",
                    "latest" : "3.0.3.RELEASE"
                },
                "io.undertow:undertow-core" : {
                    "required" : "1.3.25.Final",
                    "latest" : "2.0.0.Alpha1"
                },
                "io.undertow:undertow-servlet" : {
                    "required" : "1.3.25.Final",
                    "latest" : "2.0.0.Alpha1"
                },
                "io.undertow:undertow-websockets-jsr" : {
                    "required" : "1.3.25.Final",
                    "latest" : "2.0.0.Alpha1"
                },
                "javax.cache:cache-api" : {
                    "required" : "1.0.0",
                    "latest" : "1.0.0-PFD"
                },
                "javax.servlet:javax.servlet-api" : {
                    "required" : "3.1.0",
                    "latest" : "4.0.0-b01"
                },
                "joda-time:joda-time" : {
                    "required" : "2.9.5",
                    "latest" : "2.9.6"
                },
                "mysql:mysql-connector-java" : {
                    "required" : "5.1.40",
                    "latest" : "6.0.5"
                },
                "net.sourceforge.htmlunit:htmlunit" : {
                    "required" : "2.21",
                    "latest" : "2.23"
                },
                "nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect" : {
                    "required" : "1.4.0",
                    "latest" : "2.1.1"
                },
                "org.apache.activemq:activemq-amqp" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-blueprint" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-broker" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-camel" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-client" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-console" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-http" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-jaas" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-jdbc-store" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-jms-pool" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-kahadb-store" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-karaf" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-leveldb-store" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-log4j-appender" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-mqtt" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-openwire-generator" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-openwire-legacy" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-osgi" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-partition" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-pool" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-ra" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-run" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-runtime-config" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-shiro" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-spring" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-stomp" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:activemq-web" : {
                    "required" : "5.13.4",
                    "latest" : "5.14.1"
                },
                "org.apache.activemq:artemis-amqp-protocol" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-commons" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-core-client" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-jms-client" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-jms-server" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-journal" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-native" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-selector" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-server" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.activemq:artemis-service-extensions" : {
                    "required" : "1.3.0",
                    "latest" : "1.5.0"
                },
                "org.apache.derby:derby" : {
                    "required" : "10.12.1.1",
                    "latest" : "10.13.1.1"
                },
                "org.apache.logging.log4j:log4j-api" : {
                    "required" : "2.6.2",
                    "latest" : "2.7"
                },
                "org.apache.logging.log4j:log4j-core" : {
                    "required" : "2.6.2",
                    "latest" : "2.7"
                },
                "org.apache.logging.log4j:log4j-slf4j-impl" : {
                    "required" : "2.6.2",
                    "latest" : "2.7"
                },
                "org.apache.solr:solr-solrj" : {
                    "required" : "5.5.3",
                    "latest" : "6.3.0"
                },
                "org.apache.tomcat:tomcat-jdbc" : {
                    "required" : "8.5.6",
                    "latest" : "9.0.0.M13"
                },
                "org.apache.tomcat:tomcat-jsp-api" : {
                    "required" : "8.5.6",
                    "latest" : "9.0.0.M13"
                },
                "org.apache.tomcat.embed:tomcat-embed-core" : {
                    "required" : "8.5.6",
                    "latest" : "9.0.0.M13"
                },
                "org.apache.tomcat.embed:tomcat-embed-el" : {
                    "required" : "8.5.6",
                    "latest" : "9.0.0.M13"
                },
                "org.apache.tomcat.embed:tomcat-embed-jasper" : {
                    "required" : "8.5.6",
                    "latest" : "9.0.0.M13"
                },
                "org.apache.tomcat.embed:tomcat-embed-websocket" : {
                    "required" : "8.5.6",
                    "latest" : "9.0.0.M13"
                },
                "org.assertj:assertj-core" : {
                    "required" : "2.5.0",
                    "latest" : "3.6.0"
                },
                "org.codehaus.janino:janino" : {
                    "required" : "2.7.8",
                    "latest" : "3.0.6"
                },
                "org.eclipse.jetty:apache-jsp" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:apache-jstl" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-annotations" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-client" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-continuation" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-deploy" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-http" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-io" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-jmx" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-plus" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-security" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-server" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-servlet" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-servlets" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-util" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-webapp" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty:jetty-xml" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty.websocket:javax-websocket-server-impl" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty.websocket:websocket-client" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.eclipse.jetty.websocket:websocket-server" : {
                    "required" : "9.3.14.v20161028",
                    "latest" : "9.4.0.RC2"
                },
                "org.ehcache:ehcache" : {
                    "required" : "3.1.3",
                    "latest" : "3.2.0.beta1"
                },
                "org.ehcache:ehcache-clustered" : {
                    "required" : "3.1.3",
                    "latest" : "3.2.0.beta1"
                },
                "org.ehcache:ehcache-transactions" : {
                    "required" : "3.1.3",
                    "latest" : "3.2.0.beta1"
                },
                "org.elasticsearch:elasticsearch" : {
                    "required" : "2.4.1",
                    "latest" : "5.0.1"
                },
                "org.firebirdsql.jdbc:jaybird-jdk17" : {
                    "required" : "2.2.11",
                    "latest" : "3.0.0-beta-1"
                },
                "org.firebirdsql.jdbc:jaybird-jdk18" : {
                    "required" : "2.2.11",
                    "latest" : "3.0.0-beta-1"
                },
                "org.flywaydb:flyway-core" : {
                    "required" : "3.2.1",
                    "latest" : "4.0.3"
                },
                "org.glassfish:javax.el" : {
                    "required" : "3.0.0",
                    "latest" : "3.0.1-b08"
                },
                "org.glassfish.jersey.containers:jersey-container-servlet" : {
                    "required" : "2.23.2",
                    "latest" : "2.24.1"
                },
                "org.glassfish.jersey.containers:jersey-container-servlet-core" : {
                    "required" : "2.23.2",
                    "latest" : "2.24.1"
                },
                "org.glassfish.jersey.core:jersey-server" : {
                    "required" : "2.23.2",
                    "latest" : "2.24.1"
                },
                "org.glassfish.jersey.ext:jersey-bean-validation" : {
                    "required" : "2.23.2",
                    "latest" : "2.24.1"
                },
                "org.glassfish.jersey.ext:jersey-spring3" : {
                    "required" : "2.23.2",
                    "latest" : "2.24.1"
                },
                "org.glassfish.jersey.media:jersey-media-json-jackson" : {
                    "required" : "2.23.2",
                    "latest" : "2.24.1"
                },
                "org.hibernate:hibernate-core" : {
                    "required" : "5.0.11.Final",
                    "latest" : "5.2.4.Final"
                },
                "org.hibernate:hibernate-ehcache" : {
                    "required" : "5.0.11.Final",
                    "latest" : "5.2.4.Final"
                },
                "org.hibernate:hibernate-entitymanager" : {
                    "required" : "5.0.11.Final",
                    "latest" : "5.2.4.Final"
                },
                "org.hibernate:hibernate-envers" : {
                    "required" : "5.0.11.Final",
                    "latest" : "5.2.4.Final"
                },
                "org.hibernate:hibernate-java8" : {
                    "required" : "5.0.11.Final",
                    "latest" : "5.2.4.Final"
                },
                "org.hibernate:hibernate-jpamodelgen" : {
                    "required" : "5.0.11.Final",
                    "latest" : "5.2.4.Final"
                },
                "org.hibernate:hibernate-validator" : {
                    "required" : "5.2.4.Final",
                    "latest" : "5.3.3.Final"
                },
                "org.hibernate:hibernate-validator-annotation-processor" : {
                    "required" : "5.2.4.Final",
                    "latest" : "5.3.3.Final"
                },
                "org.hornetq:hornetq-jms-client" : {
                    "required" : "2.4.7.Final",
                    "latest" : "2.5.0.Beta1"
                },
                "org.hornetq:hornetq-jms-server" : {
                    "required" : "2.4.7.Final",
                    "latest" : "2.5.0.Beta1"
                },
                "org.hsqldb:hsqldb" : {
                    "required" : "2.3.3",
                    "latest" : "2.3.4"
                },
                "org.infinispan:infinispan-jcache" : {
                    "required" : "8.2.4.Final",
                    "latest" : "9.0.0.Alpha4"
                },
                "org.infinispan:infinispan-spring4-common" : {
                    "required" : "8.2.4.Final",
                    "latest" : "9.0.0.Alpha4"
                },
                "org.infinispan:infinispan-spring4-embedded" : {
                    "required" : "8.2.4.Final",
                    "latest" : "9.0.0.Alpha4"
                },
                "org.javassist:javassist" : {
                    "required" : "3.20.0-GA",
                    "latest" : "3.22.0-CR1"
                },
                "org.jboss:jboss-transaction-spi" : {
                    "required" : "7.3.4.Final",
                    "latest" : "7.5.0.Final"
                },
                "org.jboss.narayana.jta:jdbc" : {
                    "required" : "5.3.5.Final",
                    "latest" : "5.4.0.Final"
                },
                "org.jboss.narayana.jta:jta" : {
                    "required" : "5.3.5.Final",
                    "latest" : "5.4.0.Final"
                },
                "org.jboss.narayana.jts:narayana-jts-integration" : {
                    "required" : "5.3.5.Final",
                    "latest" : "5.4.0.Final"
                },
                "org.jolokia:jolokia-core" : {
                    "required" : "1.3.3",
                    "latest" : "1.3.5"
                },
                "org.jooq:jooq" : {
                    "required" : "3.8.5",
                    "latest" : "3.8.6"
                },
                "org.jooq:jooq-codegen" : {
                    "required" : "3.8.5",
                    "latest" : "3.8.6"
                },
                "org.jooq:jooq-meta" : {
                    "required" : "3.8.5",
                    "latest" : "3.8.6"
                },
                "org.json:json" : {
                    "required" : "20140107",
                    "latest" : "20160810"
                },
                "org.mariadb.jdbc:mariadb-java-client" : {
                    "required" : "1.4.6",
                    "latest" : "1.5.5"
                },
                "org.mockito:mockito-core" : {
                    "required" : "1.10.19",
                    "latest" : "2.2.22"
                },
                "org.mongodb:mongo-java-driver" : {
                    "required" : "3.2.2",
                    "latest" : "3.4.0-rc1"
                },
                "org.mongodb:mongodb-driver" : {
                    "required" : "3.2.2",
                    "latest" : "3.4.0-rc1"
                },
                "org.mortbay.jasper:apache-el" : {
                    "required" : "8.0.33",
                    "latest" : "8.5.5"
                },
                "org.neo4j:neo4j-ogm-api" : {
                    "required" : "2.0.5",
                    "latest" : "2.1.0-M01"
                },
                "org.neo4j:neo4j-ogm-compiler" : {
                    "required" : "2.0.5",
                    "latest" : "2.1.0-M01"
                },
                "org.neo4j:neo4j-ogm-core" : {
                    "required" : "2.0.5",
                    "latest" : "2.1.0-M01"
                },
                "org.neo4j:neo4j-ogm-http-driver" : {
                    "required" : "2.0.5",
                    "latest" : "2.1.0-M01"
                },
                "org.seleniumhq.selenium:htmlunit-driver" : {
                    "required" : "2.21",
                    "latest" : "2.23.2"
                },
                "org.seleniumhq.selenium:selenium-api" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-chrome-driver" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-firefox-driver" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-ie-driver" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-java" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-remote-driver" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-safari-driver" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.seleniumhq.selenium:selenium-support" : {
                    "required" : "2.53.1",
                    "latest" : "3.0.1"
                },
                "org.skyscreamer:jsonassert" : {
                    "required" : "1.3.0",
                    "latest" : "1.4.0"
                },
                "org.spockframework:spock-core" : {
                    "required" : "1.0-groovy-2.4",
                    "latest" : "1.1-groovy-2.4-rc-3"
                },
                "org.spockframework:spock-spring" : {
                    "required" : "1.0-groovy-2.4",
                    "latest" : "1.1-groovy-2.4-rc-3"
                },
                "org.springframework.hateoas:spring-hateoas" : {
                    "required" : "0.20.0.RELEASE",
                    "latest" : "0.21.0.RELEASE"
                },
                "org.springframework.integration:spring-integration-java-dsl" : {
                    "required" : "1.1.4.RELEASE",
                    "latest" : "1.2.1.RELEASE"
                },
                "org.springframework.retry:spring-retry" : {
                    "required" : "1.1.4.RELEASE",
                    "latest" : "1.1.5.RELEASE"
                },
                "org.springframework.security:spring-security-acl" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-aspects" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-cas" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-config" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-core" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-crypto" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-data" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-jwt" : {
                    "required" : "1.0.5.RELEASE",
                    "latest" : "1.0.6.RELEASE"
                },
                "org.springframework.security:spring-security-ldap" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-messaging" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-openid" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-remoting" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-taglibs" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-test" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.security:spring-security-web" : {
                    "required" : "4.1.3.RELEASE",
                    "latest" : "4.2.0.RELEASE"
                },
                "org.springframework.ws:spring-ws-core" : {
                    "required" : "2.3.1.RELEASE",
                    "latest" : "2.4.0.RELEASE"
                },
                "org.springframework.ws:spring-ws-security" : {
                    "required" : "2.3.1.RELEASE",
                    "latest" : "2.4.0.RELEASE"
                },
                "org.springframework.ws:spring-ws-support" : {
                    "required" : "2.3.1.RELEASE",
                    "latest" : "2.4.0.RELEASE"
                },
                "org.springframework.ws:spring-ws-test" : {
                    "required" : "2.3.1.RELEASE",
                    "latest" : "2.4.0.RELEASE"
                },
                "org.thymeleaf:thymeleaf" : {
                    "required" : "2.1.5.RELEASE",
                    "latest" : "3.0.2.RELEASE"
                },
                "org.thymeleaf:thymeleaf-spring4" : {
                    "required" : "2.1.5.RELEASE",
                    "latest" : "3.0.2.RELEASE"
                },
                "org.thymeleaf.extras:thymeleaf-extras-java8time" : {
                    "required" : "2.1.0.RELEASE",
                    "latest" : "3.0.0.RELEASE"
                },
                "org.thymeleaf.extras:thymeleaf-extras-springsecurity4" : {
                    "required" : "2.1.2.RELEASE",
                    "latest" : "3.0.1.RELEASE"
                },
                "org.webjars:hal-browser" : {
                    "required" : "9f96c74",
                    "latest" : "3325375"
                },
                "org.xerial:sqlite-jdbc" : {
                    "required" : "3.8.11.2",
                    "latest" : "3.15.1"
                },
                "redis.clients:jedis" : {
                    "required" : "2.8.2",
                    "latest" : "2.9.0"
                },
                "xml-apis:xml-apis" : {
                    "required" : "1.4.01",
                    "latest" : "2.0.2"
                }
            }
        };
    }]);
