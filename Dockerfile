FROM payara/server-web

COPY target/Soften-1.0-SNAPSHOT.war $DEPLOY_DIR
 
