package be.wimbervoets.junit5.extensions

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.extension.*

/*
    This JUnit 5 Extension will start and stop a single use MockWebserver before and after a single test
    It'll run on a random free port number decided by the MockWebserver itself, so it is possible to run MockWebserver tests in parallel

    The started MockWebserver and associated base HttpUrl is stored in the JUnit5 extension context using the unique id of the current test as key

    This extension also adds support for using MockWebServer and base HttpUrl as a parameter of any of your test functions. It'll return the same instance as the one that was created in beforeEach

    Return the base Http URL is handy if the test needs to use this dynamic url (for example reconfiguring a Retrofit service baseUrl)
 */

class MockWebServerExtension : BeforeEachCallback, AfterEachCallback, BeforeAllCallback, ParameterResolver {

    private lateinit var namespace: ExtensionContext.Namespace

    override fun beforeAll(context: ExtensionContext?) {
        namespace = ExtensionContext.Namespace.create(MockWebServerExtension::class.java)
    }

    override fun beforeEach(context: ExtensionContext?) {
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        // Store the mockwebserver and http url in the extension context with a unique id of the current test
        val store = context?.getStore(namespace)
        store?.put("${context.uniqueId}-mockWebServer", mockWebServer)
        store?.put("${context.uniqueId}-mockHttpUrl", mockWebServer.url(""))
    }

    override fun afterEach(context: ExtensionContext?) {
        // Get the mockwebserver from the context store
        val store = context?.getStore(namespace)
        val key = "${context?.uniqueId}-mockWebServer"
        val mockWebServer = store?.get(key) as MockWebServer
        // shutdown the mockwebserver
        mockWebServer.shutdown()

        // remove it from the store
        store.remove(key)
    }

    override fun supportsParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Boolean {
        return parameterContext?.parameter?.type == MockWebServer::class.java ||
            parameterContext?.parameter?.type == HttpUrl::class.java
    }

    override fun resolveParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Any {
        when (parameterContext?.parameter?.type) {
            // Get the store namespaced to this extension class name
            MockWebServer::class.java -> {
                // Get the object of type MockWebServer that is present in this Store keyed by uniqueId-MockWebserver
                return extensionContext?.getStore(namespace)?.get(
                    "${extensionContext.uniqueId}-mockWebServer"
                ) as MockWebServer
            }
            HttpUrl::class.java -> {
                // Get the object of type HttpUrl that is present in this Store keyed by uniqueId-MockWebserver
                return extensionContext?.getStore(namespace)?.get(
                    "${extensionContext.uniqueId}-mockHttpUrl"
                ) as HttpUrl
            }
        }
        return Unit
    }
}
