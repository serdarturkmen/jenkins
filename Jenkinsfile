#!/usr/bin/env groovy

node {

    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

     stage('docker-build') {
        sh "./mvnw package -Pprod verify jib:dockerBuild"
    }


}

def notifyChannel(text) {
    try {
        // Slack channel #acms-parser
        def slackURL = "https://hooks.slack.com/services/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"
        def json = "{\"blocks\": [{\"type\": \"section\", \"text\": {\"type\": \"mrkdwn\", \"text\": \":x: hello\"}}, {\"type\": \"context\", \"elements\": [{\"type\": \"mrkdwn\", \"text\": \"Build URL: ${BUILD_URL}\"}]}]}"

        sh "curl -s -X POST -H 'Content-type: application/json' --data '${json}' ${slackURL}"

    } catch (error) {
        echo error
    }
}
