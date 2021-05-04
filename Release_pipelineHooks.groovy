/*
 * Supported pipeline hooks:
 * - beforePrepare
 * - afterPrepare
 * - beforePublish
 * - afterPublish
 * - runStagingTests
 *
 * You can find a list of available properties in the main pipeline description: https://bitbucket.booxdev.com/projects/JP/repos/jenkins-microservice-pipeline/browse/README.md
 * The supported hooks are at least defined as empty method to avoid Warnings about non-found methods
 */

def beforePrepare(Map propertiesMap) {
    // empty
}

def afterPrepare(Map propertiesMap) {
    // empty
}

def beforePublish(Map propertiesMap) {
    // empty
}

def afterPublish(Map propertiesMap) {
    // empty
}

def runStagingTests(Map propertiesMap) {
    // empty
}

return this
