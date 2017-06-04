name := """crud"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = project.in(file(".")).enablePlugins(PlayScala).enablePlugins(DebianPlugin)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

libraryDependencies ++= Seq(
  "com.typesafe.play"   %%   "play-slick"              %   "2.0.0",
  "com.typesafe.play"   %%   "play-slick-evolutions"   %   "2.0.0",
  "com.typesafe.play"   %% 	 "play-mailer" 			   %   "5.0.0"
)
