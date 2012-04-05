#!/bin/bash

supercollider_dir=$1
build_type=$2

version_string=`grep PROJECT_VERSION $supercollider_dir/SCVersion.txt  | xargs echo`
regex='set\(PROJECT_VERSION_MAJOR (.*)\) set\(PROJECT_VERSION_MINOR (.*)\) set\(PROJECT_VERSION_PATCH (.*)\)'

if [[ $version_string =~ $regex ]]; then
    install="${BASH_REMATCH[1]}.${BASH_REMATCH[2]}${BASH_REMATCH[3]}"
else
    echo
    echo Invalid Supercollider install.  It should be here:
    echo $supercollider_dir
    exit 1
fi

echo You are about to build SuperCollider version $install for $build_type
read -p "Is that the correct version (y/n)?" CONT
if [ "$CONT" == "n" ]; then
    echo "Exiting. Please checkout the correct version of SuperCollider."
    exit 1
else
    echo SuperCollider Version $install >sc-version.txt
fi

exit 0
