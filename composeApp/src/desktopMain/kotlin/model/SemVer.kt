package model

data class SemVer(val major: Int, val minor: Int, val patch: Int) : Comparable<SemVer> {
	override fun compareTo(other: SemVer): Int {
		return compareValuesBy(this, other, SemVer::major, SemVer::minor, SemVer::patch)
	}
}
