plugins {
  // Apply the java Plugin to add support for Java.
  java
}

group = "io.documentnode"

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
}

dependencies {
  testImplementation("org.testng:testng:7.3.0")
  testImplementation("org.assertj:assertj-core:3.18.0")
  testImplementation("org.mockito:mockito-core:3.6.0")
}

tasks.test {
  // Use TestNG for unit tests.
  useTestNG()
}
