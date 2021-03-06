package jenkinsci.plugins.icqbot.actions

import hudson.Extension
import hudson.FilePath
import hudson.Launcher
import hudson.model.AbstractProject
import hudson.model.Run
import hudson.model.TaskListener
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder
import jenkins.tasks.SimpleBuildStep
import jenkinsci.plugins.icqbot.ICQBot
import jenkinsci.plugins.icqbot.ICQRecipient
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.kohsuke.stapler.DataBoundConstructor
import java.io.IOException

@ObsoleteCoroutinesApi
@Suppress("MemberVisibilityCanBePrivate")
class SendMessageBuildStep @DataBoundConstructor
constructor(
    val message: String,
    val recipients: List<ICQRecipient>
) : Builder(), SimpleBuildStep {

  @Throws(InterruptedException::class, IOException::class)
  override fun perform(run: Run<*, *>,
                       path: FilePath,
                       launcher: Launcher,
                       listener: TaskListener) {
    ICQBot.send(message, recipients, run, path, listener)
  }

  @Extension
  class SendMessageBuildStepDescriptor : BuildStepDescriptor<Builder>() {

    override fun isApplicable(jobType: Class<out AbstractProject<*, *>>): Boolean {
      return true
    }

    override fun getDisplayName(): String {
      return "Send message to ICQ"
    }
  }
}
