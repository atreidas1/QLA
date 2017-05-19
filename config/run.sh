#! /bin/sh

if [ -z "$JAVA_HOME" ]; then
	export JAVA_HOME=/datalex/java/
fi

export BASE_DIR=`dirname "$0"`
export JAVA_OPTS="-Dconfig.folder=$BASE_DIR/config -Xms512m -Xmx512m"
$JAVA_HOME/bin/java -jar $JAVA_OPTS $BASE_DIR/QLA-0.0.2-SNAPSHOT.jar

