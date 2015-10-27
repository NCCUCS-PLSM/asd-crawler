lazy val root = (project in file("."))
  .settings(Settings.default: _*)
  .settings(Settings.plugin: _*)
  .settings(
    name := "asd-crawler-root"
  ).dependsOn(warehouse, api)

lazy val warehouse = (project in file("warehouse"))
  .settings(Settings.default: _*)
  .settings(Settings.plugin: _*)
  .settings(
    name := "asd-crawler-warehouse",
    libraryDependencies ++= Dependencies.warehouseDependencies
  )

lazy val api = (project in file("api"))
  .settings(Settings.default: _*)
  .settings(Settings.plugin: _*)
  .settings(
    name := "asd-crawler-api",
    libraryDependencies ++= Dependencies.apiDependencies
  )