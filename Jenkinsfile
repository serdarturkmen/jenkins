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
            withSonarQubeEnv('SonarQube Scanner') {
                sh "./mvnw clean install"
                currentBuild.result = 'SUCCESS'
            }
        }

        stage('sonar') {
            withSonarQubeEnv() {
                 sh "./mvnw sonar:sonar -Dsonar.host.url=http://192.168.10.94:9001 -Dsonar.login=ac637e6a33f53f5bab34e6f48b764429c2a1cbbc"
                 currentBuild.result = 'SUCCESS'
            }
        }

        stage("Quality Gate"){
          timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
            def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
            if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
          }
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
