@echo off

set cp=.\bin\;.\build\classes
for %%i in (".\lib\*.jar") do call setenv.bat %%i
set cp=%cp%

java -classpath %cp% -Djava.library.path=.\;.\lib -Xms16m -Xmx512m com.job.CheckRecordJob
::java -classpath %cp% -Djava.library.path=.\;.\lib -Xms16m -Xmx512m com.job.CheckRecordTask