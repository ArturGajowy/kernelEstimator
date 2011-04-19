buildLatest:
	git pull
	mvn package
	echo 'Please make the native libraries under target/lib available by doing:'
	echo 'export LD_LIBRARY_PATH=target/lib'

test:
	mvn test