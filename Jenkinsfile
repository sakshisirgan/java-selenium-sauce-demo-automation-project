pipeline {
   agent any
   
   environment {
      BRANCH_NAME = 'main'
      ECLIPSE_WORKSPACE = '"C:\\Users\\saksh\\eclipse-workspace\\SwagLabsAutomationTesting"'
      COMMIT_MESSAGE = 'Jenkins: Auto-commit after build'
   }
   
   // Auto-trigger every 5 mins on Git changes
   triggers {
      pollSCM('H H * * *')
   }
   
   stages {
      stage('Checkout from Git') {
         steps {
            git branch: "${env.BRANCH_NAME}",
            url: 'https://github.com/sakshisirgan/java-selenium-sauce-demo-automation-project.git'
         }
      }
      
      stage('Copy Files from Eclipse Workspace') {
         steps {
            bat """
            echo Copying files from Eclipse workspace...
            xcopy /E /Y /I "${ECLIPSE_WORKSPACE}\\*" "."
            """
         }
      }
      
      stage('Build & Test') {
         steps {
            bat 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml'
         }
      }
      
      stage('Commit & Push Changes') {
         steps {
            script {
               echo 'Checking for changes to push...'
               withCredentials([usernamePassword(
               credentialsId: '1f706339-ebb5-4165-9b60-12bdfe859b9e',
               usernameVariable: 'GIT_USER',
               passwordVariable: 'GIT_TOKEN')]) {
                  
                  bat """
                  git config user.email "sakshisirgan03@gmail.com"
                  git config user.name "Sakshi"
                  
                  git status
                  git add .
                  
                  REM Commit only if there are changes
                  git diff --cached --quiet || git commit -m "${COMMIT_MESSAGE}"
                  
                  REM Push using token
                  git push https://%GIT_USER%:%GIT_TOKEN%@github.com/sakshisirgan/java-selenium-sauce-demo-automation-project.git ${BRANCH_NAME}
                  """
               }
            }
         }
      }
   }
   }