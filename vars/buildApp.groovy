def call() {
    echo "Building the Application..."
    sh 'mvn test'
    sh 'mvn clean package -DskipTests' 
}
