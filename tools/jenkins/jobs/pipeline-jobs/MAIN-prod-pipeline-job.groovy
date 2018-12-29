node('SLAVE') {
  stage('TEST-ENV-CREATE') {
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
  }
}
