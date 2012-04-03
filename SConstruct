# -*- python -*- =======================================================
# FILE:         SConstruct
import sys, os

env = Environment()

sourceFiles = Split("""
./src/scsynth-jna.cpp
""")
if os.path.exists(os.getcwd() + '/../supercollider/include/common'):
    env.Append(CPPPATH = ['../supercollider/include/common',
                          '../supercollider/include/plugin_interface', 
                          '../supercollider/include/server', 
                          './include'])
else:
    env.Append(CPPPATH = ['../supercollider/common/Headers/common',
                          '../supercollider/common/Headers/plugin_interface', 
                          '../supercollider/common/Headers/server', 
                          './include'])


# LINUX
env.Append(CPPDEFINES={'NOVA_SIMD' : '1',
		'SC_LINUX' : '1',
		'NDEBUG' : '1',
		'_REENTRANT' : '1',
		'SC_PLUGIN_EXT' : '\\".so\\"',
		'SC_PLUGIN_LOAD_SYM' : '\\"load\\"',
		'SC_PLUGIN_DIR' : '\\"/usr/local/lib/SuperCollider/plugins\\"',
		'SC_MEMORY_ALIGNMENT' : '16',
		'SC_JACK_USE_DLL' : 'False',
		'SC_JACK_DEBUG_DLL' : 'False',
		'SC_AUDIO_API' : 'SC_AUDIO_API_JACK'})
# "-march=i686", 
env.Append(CCFLAGS=["-Wno-unknown-pragmas", "-O3", "-ffast-math", "-fno-finite-math-only", "-fstrength-reduce", "-msse", "-mfpmath=sse", "-msse2"])
env.Append(LINKFLAGS = '-ljack -lrt -lpthread -ldl -lm -lsndfile ')
# END LINUX

env.SharedLibrary(target = "scsynth_jna", source = sourceFiles)

