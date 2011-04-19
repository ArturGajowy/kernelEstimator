buildLatest: projectDownloaded
	git pull
	mvn package

downloadProject:
	git clone git://github.com/ArturGajowy/kernelEstimator.git
	cd kernelEstimator; sh installMaven.sh doExports
	touch projectDownloaded
