#!/bin/bash

# Display the version about to be built and get user confirmation

supercollider_dir=$1
build_type=$2

version_string=`grep PROJECT_VERSION $supercollider_dir/SCVersion.txt  | xargs echo`
regex='set\(PROJECT_VERSION_MAJOR (.*)\) set\(PROJECT_VERSION_MINOR (.*)\) set\(PROJECT_VERSION_PATCH (.*)\)'

if [[ $version_string =~ $regex ]]; then
    installed_version="${BASH_REMATCH[1]}.${BASH_REMATCH[2]}${BASH_REMATCH[3]}"
else
    echo
    echo Invalid Supercollider install.  It should be here:
    echo $supercollider_dir
    exit 1
fi

echo You are about to build SuperCollider version $installed_version for $build_type
read -p "Is that the correct version (y/n)?" CONT
if [ "$CONT" == "n" ]; then
    echo "Exiting. Please checkout the correct version of SuperCollider."
    exit 1
else
    echo SuperCollider Version $installed_version >sc-version.txt
fi

exit 0
