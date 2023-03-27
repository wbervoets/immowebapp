package be.wimbervoets.junit5.extensions

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

/*
    This extension  adds support for using Json parser as a parameter of any of your test functions.
 */

class JsonParserExtension : ParameterResolver {

    private val json: Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    override fun supportsParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Boolean {
        return parameterContext?.parameter?.type == Json::class.java
    }

    override fun resolveParameter(
        parameterContext: ParameterContext?,
        extensionContext: ExtensionContext?
    ): Any {
        return json
    }
}
