node('SLAVE') {
    stage('Variables') {
        sh 'env'
    }
}