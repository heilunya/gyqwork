net use N: /D /y
net use O: /D /y
net use P: /D /y

net use N: \\192.168.x.x\f$ "123456" /user:administrator
IF %errorlevel% EQU 0 (
echo 36 normal , so use it ...
net use N: \\192.168.x.x\f$ "123456" /user:administrator
)ELSE IF NOT %errorlevel% EQU 0 (
echo 36 abnormal , so use 35 ...
net use N: \\192.168.x.x\f$ "123456" /user:administrator
)
