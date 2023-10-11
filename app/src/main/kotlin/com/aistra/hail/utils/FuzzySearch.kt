package com.aistra.hail.utils

import org.apache.commons.text.similarity.LevenshteinDistance

/**使用莱文斯坦距离 (Levenshtein distance)实现模糊搜索*/
object FuzzySearch {
    private val levenshteinDistance: LevenshteinDistance = LevenshteinDistance()

    /**
     * 字符串差异在20个字符以下 且 距离小于搜索字符串长度 且 搜索字符全部包含在搜索字符串中 则显示在搜索结果中
     * @param textToSearch 尝试匹配的字符串
     * @param query 用户输入字符串
     * */
    fun search(textToSearch: String?, query: String?): Boolean {
        if (textToSearch == null || query == null) {
            return false
        }
        val textToSearchUpp = textToSearch.uppercase()
        val queryUpp = query.uppercase()
        val d = levenshteinDistance.apply(textToSearchUpp, queryUpp)
        val lenTextToSearch = textToSearchUpp.length
        val maxD = 20
        // 字符串差异在20个字符以下 且 距离小于搜索字符串长度 且 搜索字符全部包含在搜索字符串中 则显示在搜索结果中
        return d < maxD && d < lenTextToSearch && containsAllChars(textToSearchUpp, queryUpp)
    }

    fun containsAllChars(str1: String, str2: String): Boolean {
        val charSet1 = str1.toSet()
        val charSet2 = str2.toSet()
        // 使用交集操作，如果charSet2中的所有字符都在charSet1中，返回true
        return charSet1.containsAll(charSet2)
    }
}