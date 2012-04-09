This project contains 2 pom*.xml files
- pom.xml a default Maven config. file
- pom2.xml Maven config file incluging Cobertura code coverage reporting with Surfire plugin for TestNG
	To generate reports execute:
	mvn -f pom2.xml clean test cobertura:cobertura

	Then you should expect reports generated in folders:
	core/target/site/cobertura
	util/target/site/cobertura

For both configurations you will see TestNG testing reports in folders:
core/target/surfire-reports
util/target/surfire-reports