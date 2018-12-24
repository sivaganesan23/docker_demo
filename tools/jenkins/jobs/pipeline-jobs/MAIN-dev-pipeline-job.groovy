node('SLAVE') {
  stage('CLONE') {
     git 'https://github.com/citb33/studentproj-code.git' 
   }

  stage('COMPILE'){
     sh 'mvn compile'
   }
}
