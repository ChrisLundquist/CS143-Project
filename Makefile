

CLASS =       Asteroids
JOGL_PATH =   $(HOME)/lib/jogl-2.0
CLASS_PATH =  ./bin:./lib/\*:$(JOGL_PATH)/jar/\*
NATIVE_PATH = $(JOGL_PATH)/lib
JAVA_ARGS =   -Djava.library.path=$(NATIVE_PATH) -classpath $(CLASS_PATH)

all: bin/$(CLASS).class \
	bin/Actor.class \
	bin/Asteroid.class \
	bin/Asteroids.class \
	bin/BannerPanel.class \
	bin/BasicWeapon.class \
	bin/Bullet.class \
	bin/DebrisParticle.class \
	bin/FireParticle.class \
	bin/GUI.class \
	bin/HighScoreDialog.class \
	bin/HighScores.class \
	bin/HighScoreTester.class \
	bin/ImageConverter.class \
	bin/InputHandler.class \
	bin/LifePowerUp.class \
	bin/MainMenu.class \
	bin/Particle.class \
	bin/ParticleSystem.class \
	bin/PlasmaParticle.class \
	bin/PlayerShip.class \
	bin/PowerUp.class \
	bin/ScenePanel.class \
	bin/ScorePanel.class \
	bin/Settings.class \
	bin/Shield.class \
	bin/Sound.class \
	bin/SoundEffect.class \
	bin/Sprite.class \
	bin/TripleShotPowerUp.class \
	bin/TripleShotWeapon.class \
	bin/Vector.class \
	bin/Weapon.class


bin/%.class: src/%.java bin
	javac -sourcepath ./src:$(CLASS_PATH) -classpath $(CLASS_PATH) -d bin $<

clean: bin
	rm bin/*.class

bin:
	mkdir bin

run: all
	java $(JAVA_ARGS) $(CLASS)

test: all
	java -enableassertions $(JAVA_ARGS) $(CLASS)

