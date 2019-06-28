#!/usr/bin/env groovy

node {

    try{
        stage('checkout') {
        checkout scm
        }

        stage('check java') {
            sh "java -version"
        }

        stage('build') {
            sh "./mvnw clean install"
            currentBuild.result = 'SUCCESS'
        }

        stage('sonar') {
            sh "./mvnw sonar:sonar -Dsonar.login=ca226c002fd98c2da8a96871bc9defac5edeb448"
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
        def icon = ':white_check_mark:'
        if (currentBuild.result == 'FAILURE') {
            icon = ':x:'
         }
        def committerEmail = sh(
            script: 'git --no-pager show -s --format=\'%ae\'',
            returnStdout: true).trim()
        def blamed = committerEmail
        def slackURL = "https://hooks.slack.com/services/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"
        def BUILD_URL = "serdar.com"
        def json = "{\"blocks\": [{\"type\": \"section\", \"text\": {\"type\": \"mrkdwn\", \"text\": \"${icon}  ${currentBuild.result} * Build: ${BUILD_NUMBER} - Committer: ${blamed}\"}}, {\"type\": \"context\", \"elements\": [{\"type\": \"mrkdwn\", \"text\": \"Build URL: ${BUILD_URL}\"}]}]}"

        sh "curl -s -X POST -H 'Content-type: application/json' --data '${json}' ${slackURL}"

    } catch (error) {
        echo error
    }
}
