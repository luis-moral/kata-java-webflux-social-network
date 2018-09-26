pipeline {
    agent any

    environment {
        def FARGATE_CLUSTER = "luis-social-network"
        def FARGATE_SERVICE = "social-network"
        def FARGATE_TASK_DEFINITION= "luis-social-network"
        def AWS_SUBNET = "subnet-cb3c8082"
        def AWS_SECURITY_GROUP = "sg-006cdd5f76d30c589"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git(branch: 'master', url: 'https://github.com/luis-moral/kata-java-webflux-social-network.git', poll: false)
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
                sh "docker tag luis-social-network-repository:latest 300563897675.dkr.ecr.eu-west-1.amazonaws.com/luis-social-network-repository:latest"
                sh "docker login -u AWS -p eyJwYXlsb2FkIjoiSWpsREVLNndjV0xlSWU3cHBUSmNSWE83bWh0bXA0U1M2Q2FkY2lINmwxdTc5SDV6Z2Yrd0Jtc0tTemNFSG9XeVhRVXpzczRYVHpYTU94bUlTdTFEV0Nya0ZxVUx6K0dBSEMvN3haSTFaVmJ2cmIxaHpLNGNQcFRMZ2MvWkdLTGkxRjhGTndzNE5GNXc3bHRBaElHeHVNVWNNRzBweHVuRjF5V3F3YkRranFQMm5JUnIwQ1ErbEhwRHpSWkdYbkZNb0lsZGREa3VaVnpJZHRib2lHM2tlZzJUZVNEUEZDd04vT3RWem56dDJtaFI2T3BjclkwdTRxZEVWK01nVFg5Y2Zic0NYd0ZiT2dHVzlGNGo2QlFGNzA2NmtPdVdTUVpKMmJndmNULzlDRHRnWXdlYlZGQXN5WllhYkJaZmdZeUlXeGZNQUVWSjFxMnMyTDZCZGlVWWFkK1VSTnlvSmpCMGhmWFQ0NVpNQ20vSjBQSWYwYXhDSmhUeHdCMlBCMHNnV0tBZ0pxVkVaNmhtWWd6Qy80Qmw2a0FVenBuYU4zZnNMZHdBUGJMaVZocGJabGNzc3h5TTBxOUNRNkJzMHkrdlhwY0w4NWxHd1d3elU2bk05RHlLVnFNY2NNMkhmUUUyaHlhN0dyUjc0SjBoeHc1RFVBVkh6QTA4N3VBUm1xWDhXUHpXUXhvY2ZGYWlCckxRZU5EK2FoN05jVDFpNkZ4UkpTT1FDMzVLT3hiY2hVdTFNRmlTTUFGYS9JVUNnaXBrUHkyd2NnMUdOWGRMM2g0VGZ6eGRzbTBxVEMxMEhZdjhQY1Z0VkdndnIya2c4ZkNhaGRrN0l3WnBRYnU3bDJoblJwbk95TGFJUzE4azJuSEZHTnNvM1VxWk1qdjRBVUoxQVV1MnVWK2ZVeWNpdWZmbnkxbVk3VGtXVW9mM1ZTV0pEOUMyZzNSYXRweWxFUnNseUJBZmZpZTVWQWJMSStTTzhXbURUQW1xbEZhT1lla204Y29qWC9CNFJKR1JZU1crNEVmMTBRVWt2SlVOY05RaXlZVVVOMHYrVjNDdFdpUGlRVFBHMUh0eFJlekZrMXQ3VlJKbWhCUHJHU0VXanlhWnBodFRFNkRFdzVreDhPK0R1eU5LSW9paWJmUXdONGwzM1hSTUgxZk03dEtUN3YxK3lSNjlpbDFGcUhnVHR0eXpzWVNuTnlLbnM1enU0K3lISVgzdUFIZmNhSU1kdEp1anhuUEVlMVE2d3ZiTjRWOXh3TU5RYnpyc1pwV0ZHa296enZMTUt4M05CZjRBUExxY0lWelJCR3lqZ3Z2WmF6Q2ZhR1BoSlphemJ2Z1J2UElrcjIyVzAyMW43V1gxcmpNS2hWK2I3eVF2MTFJNmMxUFJrNjhLbmRHU0Y4Q1NXblNhbitxTTk5S1loTW5HTWZ3SGx6aS9hb20zazA3WlVWNElrRzJ4YmZmazJhaHBkWElMVnlBUGVNZnEiLCJkYXRha2V5IjoiQVFFQkFIaCtkUytCbE51ME54blh3b3diSUxzMTE1eWpkK0xOQVpoQkxac3VuT3hrM0FBQUFINHdmQVlKS29aSWh2Y05BUWNHb0c4d2JRSUJBREJvQmdrcWhraUc5dzBCQndFd0hnWUpZSVpJQVdVREJBRXVNQkVFREVUTGQrUDJOcElBV05NOVR3SUJFSUE3NjRBaitJdVNzWnZwS1J2UFBnUGc2a1MxUkQ4TlRlVkN3NkpxZW40ek11cVNkYWRjajBVR0tWcmFDSFNHbFdhNDQyVFVick1hRGxvOW5hWT0iLCJ2ZXJzaW9uIjoiMiIsInR5cGUiOiJEQVRBX0tFWSIsImV4cGlyYXRpb24iOjE1Mzc5OTI3NjJ9 https://300563897675.dkr.ecr.eu-west-1.amazonaws.com"
                sh "docker push 300563897675.dkr.ecr.eu-west-1.amazonaws.com/luis-social-network-repository:latest"
            }
        }
        stage('Stop Fargate Containers') {
            steps {
                script {
                    sh "aws ecs list-services --cluster ${FARGATE_CLUSTER} > services.json"
                    def services = readJSON file: 'services.json'
                    if (services.serviceArns) {
                        sh "aws ecs update-service --cluster ${FARGATE_CLUSTER} --service ${FARGATE_SERVICE} --desired-count 0"
                    }
                }
            }
        }
        stage('Delete Fargate Service') {
            steps {
                script {
                    sh "aws ecs list-services --cluster ${FARGATE_CLUSTER} > services.json"
                    def services = readJSON file: 'services.json'
                    if (services.serviceArns) {
                        sh "aws ecs delete-service --cluster ${FARGATE_CLUSTER} --service ${services.serviceArns[0]}"
                        sleep 30
                    }
                }
            }
        }
        stage("Start New Fargate Service") {
            steps {
                sh "aws ecs create-service --cluster ${FARGATE_CLUSTER} --service-name ${FARGATE_SERVICE} --task-definition ${FARGATE_TASK_DEFINITION} --desired-count 1 --launch-type 'FARGATE' --network-configuration \"awsvpcConfiguration={subnets=[${AWS_SUBNET}],securityGroups=[${AWS_SECURITY_GROUP}],assignPublicIp=ENABLED}\""
            }
        }
    }
}