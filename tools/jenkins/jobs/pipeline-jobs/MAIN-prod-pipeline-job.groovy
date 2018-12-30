node('SLAVE') {
   stage('CLONE') {
    dir('APPCODE') {
      git 'https://github.com/citb33/studentproj-code.git' 
    }
   }

  stage('COMPILE'){
    dir('APPCODE') {
     sh 'mvn compile -D VERSION=$VERSIONNO -D TYPE=$VERSIONTYPE'
    }
   }

  stage('Code Quality Check') {
    dir('APPCODE') {
      sh 'mvn sonar:sonar -Dsonar.projectKey=citb33_studentproj-code -Dsonar.organization=citb33 -Dsonar.host.url=https://sonarcloud.io   -Dsonar.login=24dc0afa2658cdc57cdce6f5d3d7538f3ded4276 -D VERSION=$VERSIONNO -D TYPE=$VERSIONTYPE'
    }
  }

  stage('Packaging'){
    dir('APPCODE') {
      sh 'mvn package -D VERSION=$VERSIONNO -D TYPE=$VERSIONTYPE'
    }
  }
  stage('TEST-ENV-CREATE') {
    withCredentials([file(credentialsId: 'CENTOS-USER-PEM-FILE', variable: 'FILE')]) {
      sh '''cat $FILE >/home/centos/devops.pem
chmod 600 /home/centos/devops.pem
'''
    }
    try {
          dir('TERRAFORM') {
            git 'https://github.com/citb33/terraform.git'
            wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'xterm']) {
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'TERRAFORM-KEYS', usernameVariable: 'ACCESS_KEY', passwordVariable: 'SECRET_KEY']]) {
                sh '''
      export AWS_ACCESS_KEY_ID=$ACCESS_KEY
      export AWS_SECRET_ACCESS_KEY=$SECRET_KEY
      export AWS_DEFAULT_REGION=us-east-2
      cd stack-test-env
      terraform init
      terraform apply -auto-approve
      '''
              }
          }
          }
    } catch(Exception ex) {
      sh 'rm -f /home/centos/devops.pem'
    }

    dir('ANSIBLE') {
      git 'https://github.com/citb33/ansible-pull.git'
      sh '''
      ansible-playbook -i /tmp/hosts deploy.yml
      '''
    }
    

  }
}
