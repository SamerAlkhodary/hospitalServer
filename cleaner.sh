for f in */
do
    echo "Cleaning all the .class in $f file.."
    rm $f/*.class
done
rm *.class