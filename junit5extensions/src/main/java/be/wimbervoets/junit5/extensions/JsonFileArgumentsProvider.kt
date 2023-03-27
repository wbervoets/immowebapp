package be.wimbervoets.junit5.extensions

import java.util.stream.Stream
import kotlinx.serialization.json.*
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.AnnotationConsumer
import org.junit.platform.commons.util.Preconditions

class JsonFileArgumentsProvider : AnnotationConsumer<JsonFileSource>, ArgumentsProvider {
    private lateinit var jsonFile: String

    override fun accept(t: JsonFileSource) {
        jsonFile = t.jsonFile
    }

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        val inputStream = javaClass.getResourceAsStream(jsonFile)
        inputStream?.let {
            val jsonObject: JsonElement = Json.parseToJsonElement(
                it.bufferedReader().use { bufferedReader -> bufferedReader.readText() }
            )
            val argument = when (jsonObject) {
                is JsonNull -> jsonObject.jsonNull
                is JsonObject -> jsonObject.jsonObject
                is JsonArray -> jsonObject.jsonArray
                is JsonPrimitive -> jsonObject.jsonPrimitive
            }
            val arguments: Arguments = Arguments.of(argument)
            return listOf(arguments).stream()
        } ?: Preconditions.notNull(inputStream) { "Classpath resource does not exist: $jsonFile" }

        return Stream.empty()
    }
}
