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
    private const val roomDb = "androidx.room:room-runtime:${Version.roomDb}"
    private const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutine}"
    private const val lifecycleVM = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleVM}"
    private const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    private const val roomKtx = "androidx.room:room-ktx:${Version.roomKtx}"
    private const val roomKapt = "androidx.room:room-compiler:${Version.roomKtx}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    private const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Version.moshi}"
    private const val dagger = "com.google.dagger:dagger:${Version.dagger}"
    private const val daggerKapt = "com.google.dagger:dagger-compiler:${Version.dagger}"
    private const val daggerSupport = "com.google.dagger:dagger-android-support:${Version.dagger}"
    private const val daggerSupportKapt = "com.google.dagger:dagger-android-processor:${Version.dagger}"
    private const val cryptoSecure = "androidx.security:security-crypto:1.0.0"

    val dependencies: Map<String, List<String>> = mapOf(
        "implementation" to listOf(
            kotlinVersion,
            kotlinCore,
            appCompat,
            materialDesign,
            constraintLayout,
            navFragment,
            navUi,
            legacySupport,
            roomDb,
            coroutine,
            lifecycleVM,
            lifecycleLiveData,
            roomKtx,
            retrofit,
            moshiConverter,
            dagger,
            daggerSupport,
            cryptoSecure
        ),
        "testImplementation" to listOf(
            jUnit
        ),
        "androidTestImplementation" to listOf(
            extJunitVersion,
            espressoCore
        ),
        "kapt" to listOf(
            roomKapt,
            daggerKapt,
            daggerSupportKapt
        )
    )
}