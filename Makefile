buildLatest: projectDownloaded
	git pull
	mvn package

downloadProject:

	touch projectDownloaded

projectDownloaded:
	make downloadProject