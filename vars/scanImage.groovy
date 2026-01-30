def call(String imageName) {
    echo "--- Phase: Scanning Docker Image: ${imageName} ---"
    sh "trivy image --severity HIGH,CRITICAL ${imageName}"
}
