#!/usr/bin/env groovy

node {

    try{
        stage('checkout') {
        checkout scm
        }

        stage('check java') {
            sh "java -version"
            notifyChannel()
        }

        stage('docker-build') {
            sh "./mvnw package -Pprod verify jib:dockerBuild"
            currentBuild.result = 'SUCCESS'
        }

    } catch (final e){
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        notifyChannel()
        echo "finished"
    }

    


}

def notifyChannel() {
    try {
        // Slack channel #acms-parser
        def slackURL = "https://hooks.slack.com/services/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"
        def BUILD_URL = "serdar.com"
        def json = "{\"blocks\": [{\"type\": \"section\", \"text\": {\"type\": \"mrkdwn\", \"text\": \":x: ${currentBuild.result}\"}}, {\"type\": \"context\", \"elements\": [{\"type\": \"mrkdwn\", \"text\": \"Build URL: ${BUILD_URL}\"}]}]}"

        sh "curl -s -X POST -H 'Content-type: application/json' --data '${json}' ${slackURL}"

    } catch (error) {
        echo error
    }
}
