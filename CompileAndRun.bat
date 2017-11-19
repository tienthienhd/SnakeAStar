echo Compile project
javac -d bin -sourcepath src src/snake/*.java src/game/*.java src/game/states/*.java src/ai/graph/*.java src/ai/*.java src/utils/*.java

echo Running
java -cp bin game.Main