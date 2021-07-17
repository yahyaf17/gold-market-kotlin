object AppDependencies {
    private const val kotlinVersion = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"
    private const val kotlinCore = "androidx.core:core-ktx:${Version.kotlinCore}"
    private const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    private const val materialDesign = "com.google.android.material:material:${Version.materialDesign}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    private const val jUnit = "junit:junit:4.+"
    private const val extJunitVersion = "androidx.test.ext:junit:${Version.extJunitVersion}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"

    val dependencies: Map<String, List<String>> = mapOf(
        "implementation" to listOf(
            kotlinVersion,
            kotlinCore,
            appCompat,
            materialDesign,
            constraintLayout
        ),
        "testImplementation" to listOf(
            jUnit
        ),
        "androidTestImplementation" to listOf(
            extJunitVersion,
            espressoCore
        )
    )
}