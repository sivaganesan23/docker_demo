node('SLAVE') {
  stage('CLONE') {
     git 'https://github.com/citb33/studentproj-code.git' 
   }

  stage('COMPILE'){
     sh 'mvn compile'
   }

  stage('Code Quality Check') {
    sh 'mvn sonar:sonar -Dsonar.host.url=http://52.15.75.213:9000 -Dsonar.login=264927da4ae2e638337076b232cd2e1687bdca28'
  }
}
