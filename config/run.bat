
set BASE_FOLDER=%cd%
set JAVA_OPTS=-Dbase.folder=%BASE_FOLDER% -Xms512m -Xmx512m
java -jar %JAVA_OPTS% QLA-0.0.2-SNAPSHOT.jar