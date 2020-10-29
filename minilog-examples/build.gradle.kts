plugins {
  id("io.documentnode.minilog.java-application-conventions")
}

dependencies {
  implementation(project(":minilog"))
}

application {
  // Define the main class for the application.
  mainClass.set("io.documentnode.minilog.Example")
}
