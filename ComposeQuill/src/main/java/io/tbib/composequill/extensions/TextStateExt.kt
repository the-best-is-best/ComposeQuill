package io.tbib.composequill.extensions

fun String.fixHtml(): String {
    var html = this

    // Function to fix specific HTML issues
    fun fixHtmlIssues(html: String): String {
        var fixedHtml = html

        // Fix unclosed <sup> and <sub> tags
        fixedHtml =
            fixedHtml.replace("""(<sup>.*?<\/sup>)|(<sub>.*?<\/sub>)""".toRegex(RegexOption.IGNORE_CASE)) {
                val content = it.value
                val tag = if (content.contains("<sup>", true)) "sup" else "sub"
                "<$tag>${content.removeSurrounding("<$tag>", "</$tag>")}</$tag>"
        }

        // Fix <sub> tags followed by <span> tags
        fixedHtml =
            fixedHtml.replace("""(<sub>)(.*?)(<span[^>]*>)""".toRegex(RegexOption.IGNORE_CASE)) {
                "${it.groups[1]?.value ?: ""}${it.groups[2]?.value ?: ""}</sub>${it.groups[3]?.value ?: ""}"
            }

        // Fix <sup> tags followed by <span> tags
        fixedHtml =
            fixedHtml.replace("""(<sup>)(.*?)(<span[^>]*>)""".toRegex(RegexOption.IGNORE_CASE)) {
                "${it.groups[1]?.value ?: ""}${it.groups[2]?.value ?: ""}</sup>${it.groups[3]?.value ?: ""}"
            }

        // Fix nested <sup> and <sub> tags issues with spans
        fixedHtml = fixedHtml.replace("""<(/?)(sup|sub)>""".toRegex(RegexOption.IGNORE_CASE)) {
            val slash = it.groups[1]?.value ?: ""
            val tag = it.groups[2]?.value ?: ""
            "</$tag><$tag>"
        }

        // Remove incorrect nested sup/sub tags
        fixedHtml = fixedHtml.replace("</sup><sup>", "").replace("</sub><sub>", "")

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
