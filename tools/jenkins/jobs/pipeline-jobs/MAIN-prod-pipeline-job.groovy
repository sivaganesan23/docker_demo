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
    dir('TERRAFORM') {
      git 'https://github.com/citb33/terraform.git'
      sh '''
cd stack-test-env
terraform init
terraform apply -auto-approve
'''
    }

  }
}
