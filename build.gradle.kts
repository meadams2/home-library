plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(group="com.jayway.jsonpath", name="json-path",version= "2.9.0")
    implementation(group="net.minidev", name="json-smart", version= "2.5.2")
    implementation(group="org.slf4j", name="slf4j-nop",version="2.0.9")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

javafx{
    version = "22"
    modules("javafx.controls", "javafx.fxml")
    configuration="implementation"
}

application{
    mainClass.set("edu.bsu.cs.graphic.HomeLibraryGUI")
}

tasks.named<JavaExec>("run"){
    jvmArgs = listOf( "--module-path", classpath.asPath,
        "--add-modules", "javafx.controls,javafx.fxml")
}