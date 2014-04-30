name := "solr-stream"

version := "0.1"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:higherKinds", "-language:existentials", "-language:postfixOps")

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies += "org.scalaz.stream" %% "scalaz-stream" % "0.4.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.3.3"

