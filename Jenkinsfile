pipeline {
  agent any
  node {
    stages {
      stage('Clone Repository') {
        steps {
          git(branch: 'master', url: 'https://github.com/luis-moral/kata-java-webflux-social-network.git', poll: true)
        }
      }
      stage('Build Artifact') {
        steps {
          sh './gradlew build bootJar -x test'
        }
      }
      stage('Build Docker') {
        steps {
          sh './gradlew clean bootJar -x test'
        }
      }
      stage('Push Docker') {
        steps {
          sh './docker tag luis-social-network-repository:latest 300563897675.dkr.ecr.eu-west-1.amazonaws.com/luis-social-network-repository:latest'
        }
      }
    }
  }
}
