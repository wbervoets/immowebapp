package be.wimbervoets.immowebapp.extensions

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/*
    Livedata internally uses ArchTaskExecutor to manage executing code on the main thread or other threads
    When running tests, the Android UI thread is not available, resulting in unit tests to fail with "Method getMainLooper in android.os.Looper not mocked."

    This extension sets a delegate to ArchTaskExecutor so it uses our implementation which immediately executes the runnable on the current thread
 */

class LivedataArchTaskExecutorExtension : BeforeAllCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(
            object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }

                override fun isMainThread(): Boolean {
                    return true
                }

                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }
            }
        )
    }

    override fun afterAll(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}
