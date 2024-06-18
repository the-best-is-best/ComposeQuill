package io.tbib.composequill.extensions

fun String.fixHtml(): String {
    var html = this

    // Function to fix specific HTML issues
    fun fixHtmlIssues(html: String): String {
        var fixedHtml = html

        // Fix unclosed <sup> tags
        fixedHtml = fixedHtml.replace("""<sup>(.*?)</sup>""".toRegex(RegexOption.IGNORE_CASE)) {
            "<sup>${it.groups[1]?.value ?: ""}</sup>"
        }

        // Fix <sub> tags followed by <span> tags
        fixedHtml =
            fixedHtml.replace("""(<sub>)(.*?)(<span[^>]*>)""".toRegex(RegexOption.IGNORE_CASE)) {
                "${it.groups[1]?.value ?: ""}${it.groups[2]?.value ?: ""}</sub>${it.groups[3]?.value ?: ""}"
            }

        return fixedHtml
    }

    // Regex pattern to remove baseline-shift styles
    val regexBaselineShift = Regex("""\s*baseline-shift\s*:\s*[^;]+;""")

    // Apply specific fixes
    html = fixHtmlIssues(html)

    // Remove baseline-shift styles
    html = html.replace(regexBaselineShift, "")

    return html
}