def call(String imageName, String dockerhubCredID) {
    echo "--- Building Docker Image: ${imageName} ---"
    sh "docker build -t ${imageName} ."

    echo "--- Phase: Scanning Docker Image: ${imageName} ---"
    sh "trivy image --severity HIGH,CRITICAL --timeout 20m ${imageName}"
    
    echo "--- Pushing Image to Docker Hub ---"
    withCredentials([usernamePassword(credentialsId: dockerhubCredID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            docker login -u "${DOCKER_USER}" -p "${DOCKER_PASS}"
            docker push ${imageName}
        """
    }

    echo "--- Removing Local Image to save space ---"
    sh "docker rmi ${imageName}"
}
