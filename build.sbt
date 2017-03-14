name := """AnimalRescue"""
organization := "com.emakarovas"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" % "scalatestplus-play_2.11" % "2.0.0-M1"
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.12.1"
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5"
libraryDependencies += "com.mohiva" %% "play-silhouette" % "3.0.0"
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "5.0.0"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.emakarovas.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.emakarovas.binders._"

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"