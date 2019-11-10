#!/usr/bin/env groovy

node('linux'){
    // Clean Up (Docker and Workspace)
    stage('Clean Up') {
        sh "docker system prune -a -f"
        
        try {
            sh "rm -rf *"
        } catch (Exception e) {}
        try {
            sh "rm .env .gitignore .npmrc .nvmrc"
        } catch (Exception e) {}
        try {
            sh "rm -rf .git"
        } catch (Exception e) {}
    }

    // Checkout code
    stage('Checkout') {
        echo 'Pulling Branch: ' + env.BRANCH_NAME
        checkout scm
    }

    // Build Image
    stage('Build') {
        sh "./dockerScripts/build.sh"
    }

    // Docker Login
    stage('Docker Login') {
        withCredentials([usernamePassword(credentialsId: 'docker-username-password', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh "docker login --username=${USERNAME} --password=${PASSWORD}"
        }
    }

    // Push Image
    stage('Push') {
        sh "./dockerScripts/push.sh"
    }

    // Docker Logout
    stage('Docker Logout') {
        sh "docker logout"
    }
    
    //  Add OC CLI
    stage('Add OC CLI') {
        def dir = pwd()
        sh "cp ~/jenkins/tools/oc-cli/oc ${dir}/"
        sh "./oc"
    }
    
    // OC Login
    stage('OC Login') {
        withCredentials([usernamePassword(credentialsId: 'oc-cli-server-token', usernameVariable: 'SERVER', passwordVariable: 'TOKEN')]) {
            sh "./oc login --token=${TOKEN} --server=${SERVER}"
        }
    }

    // OC Import Image
    stage('OC Import Image') { 
        sh "./oc import-image nodejs-ocp-demo"
    }

    // OC Logout
    stage('OC Logout') { 
        sh "./oc logout"
    }

    // Clean Up (Docker and Workspace)
    stage('Clean Up') {
        sh "docker system prune -a -f"

        try {
            sh "rm -rf *"
        } catch (Exception e) {}
        try {
            sh "rm .env .gitignore .npmrc .nvmrc"
        } catch (Exception e) {}
        try {
            sh "rm -rf .git"
        } catch (Exception e) {}
    }

}