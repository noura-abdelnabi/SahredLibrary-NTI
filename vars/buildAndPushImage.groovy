def call(String imageName, String buildNumber) {
    def fullImageName = "${imageName}:${buildNumber}"
    
    echo "Building Docker Image: ${fullImageName}"
    sh "docker build -t ${fullImageName} ."
    
    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker tag ${fullImageName} ${USER}/${fullImageName}"
        sh "docker push ${USER}/${fullImageName}"
    }
}
