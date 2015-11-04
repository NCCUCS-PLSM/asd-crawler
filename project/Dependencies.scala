import sbt._

object Dependencies {

  lazy val warehouseDependencies = Seq(
    "org.apache.hbase" % "hbase-client" % "1.1.2"
  )

  lazy val apiDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.4.0",
    "com.typesafe.akka" %% "akka-remote" % "2.4.0"
  )
}
