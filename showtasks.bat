call runcrud

if %ERRORLEVEL% == 0 goto openbrowser
echo.
echo There were errors.
goto fail

:fail
echo.
echo Breaking work.

:openbrowser
start /wait "" "http://localhost:8080/crud/v1/task/getTasks"
if %ERRORLEVEL% == 0 goto end
goto fail

:end
echo.
echo Tasks shown.
