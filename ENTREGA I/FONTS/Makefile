#NOM_VAR := path \ path
DIR_CLASSES = classes
DIR_CONTROLADOR = controladors

DIR_CLASSES_TEST = tests

JUNIT_JARS = ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar

LIBS = ./lib/junit-4.12.jar:./lib/hamcrest-core-1.3.jar

CLASS_INPUT = $(DIR_CLASSES)/*java \
				$(DIR_CONTROLADOR)/*java 

CLASS_OUTPUT = ../EXE/CLASS
TEST_OUTPUT = ../EXE/TESTS
JAR_OUTPUT = ../EXE/JAR

SRC_CLASSES = $(wildcard $(DIR_CLASSES)/*.java)
SRC_CONTROLADOR = $(wildcard $(DIR_CONTROLADOR)/*.java)

SRC_CLASSES_TEST = $(wildcard $(DIR_CLASSES_TEST)/*.java)

all: jars

domini: jars
	java -jar $(JAR_OUTPUT)DriverCtrl_Domini.jar

algoritme: jars
	#java -jar $(JAR_OUTPUT)DriverCtrl_Algoritme.jar	

jars: class controlador
	javac -cp $(CLASS_OUTPUT):$(LIBS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./drivers/DriverCtrl_Domini.java #./drivers/DriverCtrl_Algoritme.java 
	echo "Main-Class: drivers.DriverCtrl_Domini" > ./drivers/DriverCtrl_Domini.mf
	jar cmvf ./drivers/DriverCtrl_Domini.mf $(JAR_OUTPUT)DriverCtrl_Domini.jar -C $(CLASS_OUTPUT) .
	#echo "Main-Class: drivers.DriverCtrl_Algoritme" > ./drivers/DriverCtrl_Algoritme.mf 
	#jar cmvf ./drivers/DriverCtrl_Algoritme.mf $(JAR_OUTPUT)DriverCtrl_Algoritme.jar -C $(CLASS_OUTPUT) .

controlador: class $(SRC_CONTROLADOR)
	javac -cp $(CLASS_OUTPUT):$(LIBS) -d $(CLASS_OUTPUT) $(SRC_CONTROLADOR)

class: $(SRC_CLASSES)
	javac -cp $(CLASS_OUTPUT):$(LIBS) -d $(CLASS_OUTPUT) $(SRC_CLASSES)

test: jars
	javac -cp $(JUNIT_JARS):$(CLASS_OUTPUT) -d $(CLASS_OUTPUT) $(SRC_CLASSES_TEST)
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.ProducteTest
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.PrestatgeTest


javadoc:
	javadoc -cp $(CLASS_OUTPUT):$(LIBS) -d ../DOCS/javadoc/ -linksource $(CLASS_INPUT)

clean:
	rm -rf ../EXE/*