DIR_DOMINI = classes/domini
DIR_PERSISTENCIA = classes/persistencia
DIR_PRESENTACIO = classes/presentacio
DIR_CONTROLADOR = controladors
DIR_DRIVER = drivers
DIR_TESTS = ../tests

LIBS = ../lib/junit-4.13.2.jar:../lib/hamcrest-core-1.3.jar:../lib/gson-2.10.1.jar

CLASS_INPUT = $(DIR_DOMINI)/*.java \
            $(DIR_PERSISTENCIA)/*.java \
            $(DIR_PRESENTACIO)/*.java \
            $(DIR_CONTROLADOR)/*.java

SRC_FILES = $(wildcard $(DIR_DOMINI)/*.java) \
            $(wildcard $(DIR_PERSISTENCIA)/*.java) \
            $(wildcard $(DIR_PRESENTACIO)/*.java) \
            $(wildcard $(DIR_CONTROLADOR)/*.java)

SRC_DRIVER = $(wildcard $(DIR_DRIVER)/*.java)
SRC_TESTS = $(wildcard $(DIR_TESTS)/*.java)
TEST_CLASSES = AlgorismeFBTest DistribucioKruskalTest PrestatgeTest ProducteTest

JAR_OUTPUT = ../../EXE/JAR/
CLASS_OUTPUT = ../../EXE/CLASS

all: run

run: jars
	# Ejecuta el DriverCtrl_Presentacio
	java -cp $(JAR_OUTPUT)DriverCtrl_Presentacio.jar:$(LIBS) drivers.DriverCtrl_Presentacio
	# Ejecuta la PrincipalView
	java -cp $(CLASS_OUTPUT):$(LIBS) classes.presentacio.PrincipalView

jars: class
	rm -rf $(JAR_OUTPUT)
	# Crea el directorio de salida para los JAR
	mkdir -p $(JAR_OUTPUT)
	# Compila todo el código fuente y los drivers
	javac -cp $(LIBS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(SRC_DRIVER)
	# Genera el JAR con las clases compiladas
	jar cf $(JAR_OUTPUT)DriverCtrl_Presentacio.jar -C $(CLASS_OUTPUT) .


class: $(SRC_FILES)
	rm -rf $(CLASS_OUTPUT)
	# Compila todas las clases del proyecto
	javac -cp $(LIBS) -d $(CLASS_OUTPUT) $(SRC_FILES) $(SRC_DRIVER)

javadoc:
	javadoc -cp $(LIBS) -d ../../DOCS/javadoc/ -linksource $(CLASS_INPUT)

clean:
	rm -rf $(CLASS_OUTPUT)
	rm -rf $(JAR_OUTPUT)