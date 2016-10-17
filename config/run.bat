
set BASE_FOLDER=%cd%
set JAVA_OPTS=-Dconfig.folder=%BASE_FOLDER%\config\ -Xms512m -Xmx512m
java -jar %JAVA_OPTS% QLA-0.0.2-SNAPSHOT.jar