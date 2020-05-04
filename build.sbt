import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

name := """fpinscala"""

organization in ThisBuild := "asachdeva"

crossScalaVersions in ThisBuild := Seq("2.12.11", "2.13.2")

promptTheme := PromptTheme(
  List(
    text(_ => "[asachdeva]", fg(64)).padRight(" λ ")
  )
)

def uuidDep(v: String): Seq[ModuleID] =
  CrossVersion.partialVersion(v) match {
    case Some((2, 13)) => Seq.empty
    case _             => Seq(Libraries.gfcTimeuuid)
  }

lazy val commonSettings = Seq(
  startYear := Some(2020),
  libraryDependencies ++= Seq(
    compilerPlugin(Libraries.kindProjector),
    compilerPlugin(Libraries.betterMonadicFor),
    Libraries.catsEffect,
    Libraries.fs2Core,
    Libraries.http4sServer,
    Libraries.http4sDsl,
    Libraries.scalaTest % Test,
    Libraries.scalaCheck % Test
  ),
  libraryDependencies ++= uuidDep(scalaVersion.value),
  resolvers += "Apache public".at("https://repository.apache.org/content/groups/public/"),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ =>
    false
  },
  pomExtra :=
    <developers>
        <developer>
          <id>asachdeva</id>
          <name>Akshay Sachdeva</name>
          <url>https://github.com/asachdeva</url>
        </developer>
      </developers>
)

lazy val examplesDependencies = Seq(
  Libraries.http4sClient,
  Libraries.http4sCirce,
  Libraries.circeCore,
  Libraries.circeGeneric,
  Libraries.circeGenericX,
  Libraries.zioCore,
  Libraries.zioCats,
  Libraries.log4CatsSlf4j,
  Libraries.logback % Runtime
)

lazy val root = project
  .in(file("."))
  .aggregate(`fpinscala`)
  .settings(noPublish)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  skip in publish := true
)

lazy val `fpinscala` = project
  .in(file("core"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies += Libraries.http4sClient % Test)

// CI build
addCommandAlias("buildFPInScala", ";clean;+test;")
