@REM ============================================================================
@REM Maven Start Up Batch script
@REM ============================================================================

@REM Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@REM Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >nul 2>&1
if "%ERRORLEVEL%"=="0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
set CLASSPATH=%APP_HOME%\maven\wrapper\maven-wrapper.jar

set MAVEN_HOME=%APP_HOME%\maven
set MAVEN_CMD_LINE_ARGS=%*

:endInit

%MAVEN_HOME%\bin\mvn.cmd %MAVEN_CMD_LINE_ARGS%
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable MAVEN_FAILURE_EXIT_CODE if you want to control this script's exit code
if not "" == "%MAVEN_FAILURE_EXIT_CODE%" (
  exit /B %MAVEN_FAILURE_EXIT_CODE%
)

exit /B 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

exit /B 0
