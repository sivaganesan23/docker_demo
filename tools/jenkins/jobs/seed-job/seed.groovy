pipelineJob('dev-pipeline-job') {
  description('Dev-PipeLine-Job')
  displayName('Dev-PipeLine-Job')
  configure { flowdefinition ->
    flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
      'triggers' {
        'hudson.triggers.SCMTrigger' {
          'spec'('H/2 * * * *')
          'ignorePostCommitHooks'(false)
        }
      }
    }
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'('https://github.com/citb33/docker.git')
          }
        }

        'branches' {
          'hudson.plugins.git.BranchSpec' {
            'name'('*/master')
          }
        }
      }
      'scriptPath'('tools/jenkins/jobs/pipeline-jobs/MAIN-dev-pipeline-job.groovy')
      'lightweight'(true)
    }
  }
}

