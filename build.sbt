import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

name := """fpinscala"""
ThisBuild / organization := "asachdeva"
ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.7")
val MUnitFramework = new TestFramework("munit.Framework")

val format = taskKey[Unit]("Format files using scalafmt and scalafix")
promptTheme := PromptTheme(
  List(
    text(_ => "[asachdeva]", fg(64)).padRight(" λ ")
  )
)

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8", // source files are in UTF-8
  "-deprecation", // warn about use of deprecated APIs
  "-unchecked", // warn about unchecked type parameters
  "-feature", // warn about misused language features
  "-language:higherKinds", // allow higher kinded types without `import scala.language.higherKinds`
  "-language:postfixOps",
  "-Xlint", // enable handy linter warnings
  "-Xfatal-warnings", // turn compiler warnings into errors
  "-Ywarn-unused"
)

lazy val testSettings: Seq[Def.Setting[_]] = List(
  Test / parallelExecution := false,
  publish / skip := true,
  fork := true,
  testFrameworks := List(MUnitFramework),
  testOptions.in(Test) ++= {
    List(Tests.Argument(MUnitFramework, "+l", "--verbose"))
  }
)

def uuidDep(v: String): Seq[ModuleID] =
  CrossVersion.partialVersion(v) match {
    case Some((2, 13)) => Seq.empty
    case _             => Seq(Libraries.gfcTimeuuid)
  }

lazy val root = project
  .in(file("."))
  .aggregate(`fpinscala`)
  .settings(noPublish)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publish / skip := true
)

lazy val `fpinscala` = project
  .in(file("core"))
  .settings(
    testSettings,
    organization := "asachdeva",
    name := "cats-sandbox",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.7",
    libraryDependencies ++= Seq(
      Libraries.logback,
      Libraries.munit % Test
    ),
    addCompilerPlugin(Libraries.betterMonadicFor),
    testFrameworks := List(new TestFramework("munit.Framework")),
    scalafmtOnCompile := true,
    format := {
      Command.process("scalafmtAll", state.value)
      Command.process("scalafmtSbt", state.value)
    }
  )

// CI build
addCommandAlias("buildFPInScala", ";clean;+test;")
