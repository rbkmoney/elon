#!groovy
build('elon', 'java-maven') {
    checkoutRepo()
    loadBuildUtils()

    def javaLibPipeline
    runStage('load JavaLib pipeline') {
        javaLibPipeline = load("build_utils/jenkins_lib/pipeJavaLib.groovy")
    }

    def buildImageTag = "a166721af4f3454345d443bc681b91962d259d40"
    javaLibPipeline(buildImageTag)
}
