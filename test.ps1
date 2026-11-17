$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $root

$mainClasses = Join-Path $root "target\classes"
$testClasses = Join-Path $root "target\test-classes"
$sources = Get-ChildItem -Recurse -File -Filter *.java (Join-Path $root "src\main\java") |
    ForEach-Object { $_.FullName }

New-Item -ItemType Directory -Force -Path $mainClasses, $testClasses | Out-Null
& javac -encoding UTF-8 -Xlint:all -d $mainClasses $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

$testSource = Join-Path $root "src\test\java\fr\projetpo\EngineSmokeChecks.java"
& javac -encoding UTF-8 -Xlint:all -classpath $mainClasses -d $testClasses $testSource
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

& java -ea -classpath "$mainClasses;$testClasses" fr.projetpo.EngineSmokeChecks
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
