node('linux')
{
  stage ('Poll') {
   // Poll from upstream:
    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        userRemoteConfigs: [[url: "https://github.com/IBM/godsect.git"]]])
    checkout([
      $class: 'GitSCM',
      branches: [[name: '*/main']],
      doGenerateSubmoduleConfigurations: false,
      extensions: [],
      userRemoteConfigs: [[url: 'https://github.com/ZOSOpenTools/godsectport.git']]])
  }
  stage('Build') {
    build job: 'Port-Pipeline', parameters: [string(name: 'PORT_GITHUB_REPO', value: 'https://github.com/ZOSOpenTools/godsectport.git'), string(name: 'PORT_DESCRIPTION', value: 'A utility to create Go structure types from DSECT information in ADATA file created by HLASM.' ), string(name: 'BUILD_LINE', value: 'STABLE') ]
  }
}
