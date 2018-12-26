node('SLAVE') {
  stage('CLONE') {
     git 'https://github.com/citb33/studentproj-code.git' 
   }

  stage('COMPILE'){
     sh 'mvn compile'
   }

  stage('Code Quality Check') {
    sh 'mvn sonar:sonar -Dsonar.projectKey=citb33_studentproj-code -Dsonar.organization=citb33 -Dsonar.host.url=https://sonarcloud.io   -Dsonar.login=24dc0afa2658cdc57cdce6f5d3d7538f3ded4276'
  }
}
