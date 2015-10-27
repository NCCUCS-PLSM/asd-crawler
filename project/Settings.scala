import com.typesafe.sbt.SbtScalariform.ScalariformKeys.preferences
import com.typesafe.sbt.SbtScalariform._
import sbt.Keys._
import sbt._
import sbtassembly._
import sbtassembly.AssemblyKeys._

import scalariform.formatter.preferences._

object Settings {
  lazy val default = Seq(
    organization := "edu.nccu.plsm.asd",
    version := "0.1-SNAPSHOT",
    crossScalaVersions := Seq("2.11.7"),
    scalaVersion := crossScalaVersions.value.head,
    scalacOptions ++= commonScalacOptions,
    scalacOptions ++= {
      scalaVersion.value match {
        case v @ ScalaVersion("11", minor) if minor.toInt < 7 => {
          ConsoleLogger().info(s"[$v] Using Asm configuration.")
          Seq(
            "-optimise",
            "-Xverify"
          )
        }
        case v => {
          ConsoleLogger().info(s"[$v] Using GenBCode configuration.")
          Seq(
            "-Ybackend:GenBCode",
            "-Ylinearizer:dump",
            "-Yopt-inline-heuristics:everything"
          )
        }
      }
    },
    javacOptions ++= Seq(
      "-source", "1.8",
      "-target", "1.8",
      "-encoding", "UTF-8",
      "-Xlint:all",
      "-Xdoclint:all",
      "-XDignore.symbol.file",
      "-g",
      "-deprecation"
    ),
    javaOptions in run ++= Seq(
      "-Djava.net.preferIPv4Stack=true",
      "-ea"
    ),
    testOptions in Test ++= Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-oDS"),
      Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "-a")
    ),
    parallelExecution in Test := false,
    logBuffered := false,
    logLevel := Level.Info,
    persistLogLevel := Level.Info,
    ivyLoggingLevel := UpdateLogging.Default,
    resolvers ++= Seq(
      DefaultMavenRepository,
      "PLSM Maven" at "http://www.plsm.cs.nccu.edu.tw/repository/public",
      Resolver.url("PLSM Ivy", url("http://www.plsm.cs.nccu.edu.tw/repository/sbt"))(Resolver.ivyStylePatterns)
    ),
    conflictManager := ConflictManager.latestRevision,
    updateOptions := updateOptions.value.withCachedResolution(true)
  )
  lazy val plugin = scalariformSettings ++ Seq(
    preferences := preferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(PreserveDanglingCloseParenthesis, true)
      .setPreference(PreserveSpaceBeforeArguments, true)
      .setPreference(RewriteArrowSymbols, true),
    assemblyMergeStrategy in assembly := {
      case "application.conf" => MergeStrategy.concat
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    }
  )
  private[this] lazy val ScalaVersion = """^2\.([0-9]+)\.([0-9]+)?-.+$""".r
  private[this] lazy val commonScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-explaintypes",
    "-feature",
    "-g:vars",
    "-target:jvm-1.8",
    "-unchecked",
    //"-uniqid",
    "-Xcheckinit",
    "-Xexperimental",
    "-Xfuture",
    "-Xlint:_",
    "-Xlog-free-terms",
    "-Xlog-free-types",
    //"-Xlog-implicits",
    //"-Xlog-reflective-calls",
    "-Xmigration",
    "-Xprint-types",
    "-Ybreak-cycles",
    "-Yclosure-elim",
    "-Yconst-opt",
    "-Ydead-code",
    "-Yinfer-argument-types",
    "-Yinline",
    "-Yinline-handlers",
    "-Yopt:_",
    //"-Yopt-warnings:_",
    "-Yrangepos",
    //"-Yshow-symkinds",
    //"-Yshow-symowners",
    //"-Yshow-syms",
    "-Ywarn-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-inaccessible",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-Ywarn-value-discard"
  )
}