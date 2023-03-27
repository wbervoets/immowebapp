package be.wimbervoets.junit5.extensions

import org.junit.jupiter.params.provider.ArgumentsSource

@Target(AnnotationTarget.FUNCTION)
@ArgumentsSource(JsonFileArgumentsProvider::class)
annotation class JsonFileSource(val jsonFile: String) {
    /* Retention: stored in the compiled class files and visible through reflection at runtime */
}
