

CLASS =       game/MainMenu
JOGL_PATH =   $(HOME)/lib/jogl-2.0
JOAL_PATH =   $(HOME)/lib/joal-2.0
CLASS_PATH =  ./bin:./lib/\*:$(JOGL_PATH)/jar/\*:$(JOAL_PATH)/jar/\*
NATIVE_PATH = $(JOGL_PATH)/lib:$(JOAL_PATH)/lib

all: bin/$(CLASS).class


bin/%.class: src/%.java bin
	javac -sourcepath ./src:$(CLASS_PATH) -classpath $(CLASS_PATH) -d bin $<

test_bin/%.class: test/%.java test_bin
	javac -sourcepath ./test:./src:$(CLASS_PATH) -classpath $(CLASS_PATH) -d test_bin $<

clean: bin
	find ./bin -name \*.class -delete

bin:
	mkdir bin

test_bin:
	mkdir test_bin

run: all
	java -Djava.library.path=$(NATIVE_PATH) -classpath $(CLASS_PATH) game.MainMenu

.PHONY: test run clean all

test: test/physics/SpatialTreeTest.java test/physics/GJKSimplexTest.java \
	test/math/Vector3Test.java test/math/SphereTest.java \
	test/math/Matrix3fTest.java test/math/QuaternionTest.java \
	test/graphics/ModelTest.java test/graphics/WavrfrontObjLoaderTest.java \
	test/graphics/PolygonTest.java test/actor/AsteroidTest.java \
	test/actor/ActorTest.java test/actor/ActorSetTest.java test/actor/TestActor.java
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore physics.SpatialTreeTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore physics.GJKSimplexTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore math.Vector3Test
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore math.SphereTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore math.Matrix3fTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore math.QuaternionTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore graphics.ModelTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore graphics.WavrfrontObjLoaderTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore graphics.PolygonTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore actor.AsteroidTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore actor.ActorTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore actor.ActorSetTest
	java -Djava.library.path=$(NATIVE_PATH) -classpath junit.jar:./test_bin:$(CLASS_PATH) org.junit.runner.JUnitCore actor.TestActor

