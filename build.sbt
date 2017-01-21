name := """AnimalRescue"""
organization := "com.emakarovas"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.11" % "0.12.1-play24"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.emakarovas.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.emakarovas.binders._"

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"