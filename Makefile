ifeq ($(BUILD_TYPE),linux_32)
SCONS_COMMAND="scons"
LIB_SUFFIX=so
DIR_NAME=linux/x86
else

  ifeq ($(BUILD_TYPE),linux_64)
  SCONS_COMMAND="scons"
  LIB_SUFFIX=so
  DIR_NAME=linux/x86_64
  else

    ifeq ($(BUILD_TYPE),macosx)
    SCONS_COMMAND="scons -f SConstruct.macosx"
    LIB_SUFFIX=scx
    DIR_NAME=macosx/x86_64
    else
      $(error BUILD_TYPE must be linux_32, linux_64, or macosx; run like so: \
		"BUILD_TYPE=macosx make")

    endif
  endif
endif

make-all: clean make-supercollider make-cpp make-jar

clean:
	cd ../supercollider;rm -fr build; mkdir build; 
	scons -c

make-supercollider:
	./check-supercollider-version.sh ../supercollider $(BUILD_TYPE)
	cp sc-version.txt src/main/resources/supercollider/scsynth/$(DIR_NAME)/
	cd ../supercollider/build; cmake  -DSC_QT=OFF -DLIBSCSYNTH=ON -DSC_WII=OFF -DSUPERNOVA=OFF ..; make
	cp ../supercollider/build/server/scsynth/libscsynth.$(LIB_SUFFIX) src/main/resources/supercollider/scsynth/$(DIR_NAME)/
	cp ../supercollider/build/server/plugins/*.$(LIB_SUFFIX) src/main/resources/supercollider/scsynth/$(DIR_NAME)/ugens

make-cpp:
	$(SCONS_COMMAND)
	cp libscsynth_jna.$(LIB_SUFFIX) src/main/resources/supercollider/scsynth/$(DIR_NAME)/

make-jar:
	mvn compile
	mvn package

