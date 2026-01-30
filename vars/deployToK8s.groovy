def call(String imageName, String deploymentFile, String tokenID, String apiServerID) {
    echo "--- Updating Deployment File with New Image ---"

    sh "sed -i 's|image: .*|image: ${imageName}|' ${deploymentFile}"

    echo "--- Deploying to Kubernetes ---"
    withCredentials([
        string(credentialsId: tokenID, variable: 'TOKEN'),
        string(credentialsId: apiServerID, variable: 'APISERVER')
    ]) {
        sh "kubectl apply -f ${deploymentFile} --server=${APISERVER} --token=${TOKEN} --insecure-skip-tls-verify=true"
    }
}
