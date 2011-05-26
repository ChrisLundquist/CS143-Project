echo "typed up in nano, but broken in vim, and fixed in nano"
cd ..
echo "we have this many lines of code" 
git ls-files src | xargs cat | wc -l 
echo "and this many classes"
cd src
find . -type f | wc -l
