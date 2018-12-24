node('SLAVE') {
  stage('CLONE') {
     git clone 'https://github.com/citb33/studentproj-code.git' 
   }

  stage('COMPILE'){
     sh 'mvn compile'
   }
}
