resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository".at("https://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.2")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.20")
addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.2.1")
addSbtPlugin("com.scalapenos" % "sbt-prompt" % "1.0.2")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")
addSbtPlugin("org.scalameta" % "sbt-munit" % "0.7.29")
