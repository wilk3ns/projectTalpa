import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.jetbrainsCompose)
	alias(libs.plugins.compose.compiler)
}

kotlin {
	jvm("desktop")

	sourceSets {
		val desktopMain by getting

		commonMain.dependencies {
			implementation(compose.runtime)
			implementation(compose.foundation)
			implementation(compose.material)
			implementation(compose.ui)
			implementation(compose.components.resources)
			implementation(compose.components.uiToolingPreview)
			implementation(libs.androidx.material3)
			implementation(libs.material3.windowSizeClass)
			implementation(libs.kotlinx.coroutines.core)
			implementation(libs.gson)
			implementation(libs.jsoup)
			implementation(libs.json)
			implementation(libs.rss.parser)
		}
		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
		}
	}
}


compose.desktop {
	application {
		mainClass = "MainKt"

		nativeDistributions {
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
			packageName = "ee.greatstuff.talpa"
			packageVersion = "1.0.0"
		}
	}
}
