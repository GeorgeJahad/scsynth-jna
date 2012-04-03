make-all: clean make-supercollider make-cpp
	mvn compile
	mvn package

clean:
	cd ../supercollider;rm -fr build; mkdir build; 
	scons -c

make-supercollider:
	./check-supercollider-version.sh ../supercollider
	cp sc-version.txt src/main/resources/supercollider/scsynth/linux/x86/
	cd ../supercollider/build; cmake  -DSC_QT=OFF -DLIBSCSYNTH=ON -DSC_WII=OFF -DSUPERNOVA=OFF ..; make
	cp ../supercollider/build/server/scsynth/libscsynth.so src/main/resources/supercollider/scsynth/linux/x86/
	cp ../supercollider/build/server/plugins/*.so src/main/resources/supercollider/scsynth/linux/x86/ugens

make-cpp:
	scons
	cp libscsynth_jna.so src/main/resources/supercollider/scsynth/linux/x86/
