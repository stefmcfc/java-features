package uk.co.stefirby.java.features

import spock.lang.Specification

/**
 * STAGE-1-AC-06: every proposed topic package shall exist under
 * uk.co.stefirby.java.features, each containing a package-info.java.
 */
class PackageScaffoldingSpec extends Specification {

    private static final List<String> TOPIC_PACKAGES = [
            "streams",
            "collections",
            "records",
            "sealed",
            "pattern_matching",
            "switch_expressions",
            "text_blocks",
            "var",
            "optional",
            "concurrency",
            "httpclient",
            "data",
            "api",
            "qAnda",
    ]

    def "package #topicPackage exists with a package-info.java"() {
        given: "the topic package's directory and package-info.java file"
            def basePackageDir = new File("src/main/java/uk/co/stefirby/java/features")
            def packageDir = new File(basePackageDir, topicPackage)
            def packageInfo = new File(packageDir, "package-info.java")

        expect: "both the directory and the package-info.java file exist"
            packageDir.isDirectory()
            packageInfo.isFile()

        where:
            topicPackage << TOPIC_PACKAGES
    }
}
