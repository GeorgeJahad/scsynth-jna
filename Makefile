ifeq ($(BUILD_TYPE),linux_32)
SCONS_FILE="SConstruct"
LIB_SUFFIX=so
PLUGIN_SUFFIX=so
DIR_NAME=linux/x86
else

  ifeq ($(BUILD_TYPE),linux_64)
  SCONS_FILE="SConstruct"
  LIB_SUFFIX=so
  PLUGIN_SUFFIX=so
  DIR_NAME=linux/x86_64
  else

    ifeq ($(BUILD_TYPE),macosx)
    SCONS_FILE="SConstruct.macosx"
    LIB_SUFFIX=dylib
    PLUGIN_SUFFIX=scx
    DIR_NAME=macosx/x86_64
    else
      $(error BUILD_TYPE must be linux_32, linux_64, or macosx; run like so: \
		"BUILD_TYPE=macosx make")

    endif
  endif
endif

all: clean supercollider cpp jar

clean: clean-supercollider clean-cpp clean-jar

supercollider:
	./check-supercollider-version.sh ../supercollider $(BUILD_TYPE)
	cp sc-version.txt src/main/resources/supercollider/scsynth/$(DIR_NAME)
	cd ../supercollider/build; cmake  -DSC_QT=OFF -DLIBSCSYNTH=ON -DSC_WII=OFF -DSUPERNOVA=OFF ..; make
	cp ../supercollider/build/server/scsynth/libscsynth.$(LIB_SUFFIX) src/main/resources/supercollider/scsynth/$(DIR_NAME)
	cp ../supercollider/build/server/plugins/*.$(PLUGIN_SUFFIX) src/main/resources/supercollider/scsynth/$(DIR_NAME)/ugens

clean-supercollider:
	cd ../supercollider;rm -fr build; mkdir build; 

cpp:
	scons -f $(SCONS_FILE)
	cp libscsynth_jna.$(LIB_SUFFIX) src/main/resources/supercollider/scsynth/$(DIR_NAME)/

clean-cpp:
	scons -c -f $(SCONS_FILE)

jar:
	mvn compile
	mvn package

clean-jar:
	rm -fr target/*
