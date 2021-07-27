object AppDependencies {
    private const val kotlinVersion = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"
    private const val kotlinCore = "androidx.core:core-ktx:${Version.kotlinCore}"
    private const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    private const val materialDesign = "com.google.android.material:material:${Version.materialDesign}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    private const val jUnit = "junit:junit:4.+"
    private const val extJunitVersion = "androidx.test.ext:junit:${Version.extJunitVersion}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
    private const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Version.navFragment}"
    private const val navUi = "androidx.navigation:navigation-ui-ktx:${Version.navUi}"
    private const val legacySupport = "androidx.legacy:legacy-support-v${Version.legacySupport}"

    val dependencies: Map<String, List<String>> = mapOf(
        "implementation" to listOf(
            kotlinVersion,
            kotlinCore,
            appCompat,
            materialDesign,
            constraintLayout,
            navFragment,
            navUi,
            legacySupport
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