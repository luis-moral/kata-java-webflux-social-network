pipeline {
    agent any

    environment {
        def FARGATE_CLUSTER = "luis-social-network"
        def FARGATE_SERVICE = "social-network"
        def FARGATE_TASK_DEFINITION= "luis-social-network"
        def FARGATE_DESIRED_INSTANCES = "2"
        def AWS_SUBNET = "subnet-cb3c8082"
        def AWS_SECURITY_GROUP = "sg-006cdd5f76d30c589"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git(branch: 'master', url: 'https://github.com/luis-moral/kata-java-webflux-social-network.git')
            }
        }
        stage('Build Jar') {
            steps {
                sh "./gradlew clean bootJar -x test"
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t luis-social-network-repository ."
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    sh "docker tag luis-social-network-repository:latest 300563897675.dkr.ecr.eu-west-1.amazonaws.com/luis-social-network-repository:latest"
                    def loginCommand = sh (script: '(aws ecr get-login --no-include-email --region eu-west-1)', returnStdout: true).trim()
                    sh loginCommand
                    sh "docker push 300563897675.dkr.ecr.eu-west-1.amazonaws.com/luis-social-network-repository:latest"
                }
            }
        }
        stage("Create Fargate Service") {
            steps {
                script {
                    sh "aws ecs list-services --cluster ${FARGATE_CLUSTER} > services.json"
                    def services = readJSON file: 'services.json'

                    if (!services.serviceArns) {
                        sh "aws ecs create-service --cluster ${FARGATE_CLUSTER} --service-name ${FARGATE_SERVICE} --task-definition ${FARGATE_TASK_DEFINITION} --desired-count ${FARGATE_DESIRED_INSTANCES} --launch-type 'FARGATE' --network-configuration \"awsvpcConfiguration={subnets=[${AWS_SUBNET}],securityGroups=[${AWS_SECURITY_GROUP}],assignPublicIp=ENABLED}\" --cli-input-json file://ecs-service.json"
                    }
                }
            }
        }
        stage('Update Fargate Service') {
            steps {
                script {
                    sh "aws ecs update-service --cluster ${FARGATE_CLUSTER} --service ${FARGATE_SERVICE} --force-new-deployment"
                }
            }
        }
    }
}