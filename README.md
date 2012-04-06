# scsynth-jna


## Installation

### Linux

You'll need the following packages:
  
    apt-get install scons git cmake g++ curl libjack-dev maven2


You'll also have to build the following:

libsndfile-1.0.25:

    ./configure;make; sudo make install

fftw-3.3.1:

    ./configure --enable-single; make; sudo make install

You'll also need to install the supercollider source in directory above this one; something like this:

    cd ..; git clone --recursive git://supercollider.git.sourceforge.net/gitroot/supercollider/supercollider

And checkout the version you want built:
    git checkout Version-3.5

Then run the makefile like so:

BUILD_TYPE=linux_32 make


## License

scsynth-jna is published under the [GPLv2](http://www.gnu.org/licenses/gpl-2.0.html) free software license 
This is the same license as Supercollider.

