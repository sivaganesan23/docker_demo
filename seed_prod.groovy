

pipelineJob('prod-pipeline-job') {
  description('Prod-PipeLine-Job')
  displayName('Prod-PipeLine-Job')
  parameters {
    stringParam('VERSIONNO', 'NA', 'Version Number of Application')
    stringParam('VERSIONTYPE', 'RELEASE', 'Version Type is Release')
  }
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'('https://github.com/sivaganesan23/docker_demo.git')
          }
        }

        'branches' {
          'hudson.plugins.git.BranchSpec' {
            'name'('*/master')
          }
        }
      }
      'scriptPath'('Prod_pipeline.groovy')
      'lightweight'(true)
    }
  }
}
