package com.aistra.hail.utils

import org.apache.commons.text.similarity.LevenshteinDistance

/**使用莱文斯坦距离 (Levenshtein distance)实现模糊搜索*/
object FuzzySearch {
    private val levenshteinDistance: LevenshteinDistance = LevenshteinDistance()
    fun search(textToSearch: String?, query: String?): Boolean {
        if (textToSearch == null || query == null) {
            return false
        }
        val textToSearchUpp = textToSearch.uppercase()
        val queryUpp = query.uppercase()
        val d = levenshteinDistance.apply(textToSearchUpp, queryUpp)
        val lenTextToSearch = textToSearchUpp.length
        val maxD = 20
        return d < maxD && d < lenTextToSearch && containsAllChars(textToSearchUpp,queryUpp)
    }
    fun containsAllChars(str1: String, str2: String): Boolean {
        val charSet1 = str1.toSet()
        val charSet2 = str2.toSet()
        // 使用交集操作，如果charSet2中的所有字符都在charSet1中，返回true
        return charSet1.containsAll(charSet2)
    }
}