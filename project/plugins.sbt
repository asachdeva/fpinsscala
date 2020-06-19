resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository".at("https://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.12")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.3")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")
addSbtPlugin("com.scalapenos" % "sbt-prompt" % "1.0.2")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
addSbtPlugin("org.scalameta" % "sbt-munit" % "0.7.9")
