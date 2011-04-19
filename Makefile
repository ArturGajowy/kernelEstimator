buildLatest:
	git pull
	mvn package

exportLibraryPath:
	export LD_LIBRARY_PATH=target/lib

test:
	mvn test