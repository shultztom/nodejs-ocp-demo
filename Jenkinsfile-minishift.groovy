#!/usr/bin/env groovy

node('linux'){
    //  Add OC CLI
    stage('Add OC CLI') {
        def dir = pwd()
        sh "cp ~/jenkins/tools/oc-cli/oc ${dir}/"
        sh "./oc"
    }
    
    // OC Login
    stage('OC Login') {
        withCredentials([usernamePassword(credentialsId: 'oc-cli-mini', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
            sh "./oc login -u ${USER}:${PASS} -n nodejs-ocp-demo"
        }
    }

     // OC Project
    stage('OC Start Build') {
        sh "./oc project nodejs-ocp-demo"
    }

    // OC Start Build
    stage('OC Start Build') { 
        sh "./oc start-build nodejs-ocp-demo"
    }

    // OC Logout
    stage('OC Logout') { 
        sh "./oc logout"
    }
}