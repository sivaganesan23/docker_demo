node() {
    stage('Build') {
        echo 'Hello World'
        sh '''uptime
ls
pwd'''
        
    }
    
    stage('Build2') {
        echo 'Hello World- Build2'
    }
}
