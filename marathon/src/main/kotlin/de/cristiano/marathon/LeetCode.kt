package de.cristiano.marathon

import de.cristiano.marathon.dcp.utils.Constants
import java.util.*

/**
 * Challenges from <a href="https://leetcode.com/">LeetCode</a>
 */
class LeetCode {
    fun numberOfSteps(num: Int): Int {
        return when {
            num == 0 -> 0
            num % 2 == 0 -> 1 + numberOfSteps(num / 2)
            else -> 1 + numberOfSteps(num - 1)
        }
    }

    fun reverseOnlyLetters(s: String): String {
        val reversedString = s.toCharArray()
        var lowerIndex = 0
        var upperIndex = s.lastIndex

        while (lowerIndex < upperIndex) {
            if (s[lowerIndex].isLetter() && s[upperIndex].isLetter()) {
                reversedString[upperIndex] = reversedString[lowerIndex]
                    .also { reversedString[lowerIndex] = reversedString[upperIndex] }

                lowerIndex++
                upperIndex--
            }

            if (s[lowerIndex].isLetter().not()) lowerIndex++
            if (s[upperIndex].isLetter().not()) upperIndex--
        }

        return String(reversedString)
    }


    fun uniqueMorseRepresentations(words: Array<String>): Int {
        val morse = arrayOf(
            ".-",
            "-...",
            "-.-.",
            "-..",
            ".",
            "..-.",
            "--.",
            "....",
            "..",
            ".---",
            "-.-",
            ".-..",
            "--",
            "-.",
            "---",
            ".--.",
            "--.-",
            ".-.",
            "...",
            "-",
            "..-",
            "...-",
            ".--",
            "-..-",
            "-.--",
            "--.."
        )

        return words.map { it.toCharArray() }
            .map { word -> word.joinToString(separator = Constants.EMPTY) { char -> morse[char - 'a'] } }
            .toHashSet()
            .size
    }

    /**
     * [Symmetric Tree](https://leetcode.com/problems/symmetric-tree)
     *
     */
    fun isSymmetric(root: TreeNode?) = if (root == null) true else checkSides(root.left, root.right)

    private fun checkSides(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null && right == null) return true
        if (left == null || right == null) return false
        if (left.`val` != right.`val`) return false

        return checkSides(left.left, right.right) && checkSides(left.right, right.left)
    }

    fun maxTwoDigitNumber(s: String): Int {
        var max = 0

        for (i in 0 until s.lastIndex) {
            val newNumber = s.substring(i, i + 2).toInt()

            if (newNumber > max) max = newNumber
        }

        return max
    }

    fun uniqueNumberOfTimes(s: String): Int {
        var deletions = 0
        val distribution = hashMapOf<Char, Int>()

        s.map { distribution[it] = distribution.getOrDefault(it, 0) + 1 }

        for (i in 1..distribution.values.maxOrNull()!!) {
            distribution.entries.filter { it.value == i }.count()


        }

        return deletions
    }

    fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, newColor: Int): Array<IntArray> {
        floodFill(image, sr, sc, image[sr][sc], newColor)

        return image
    }

    fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, oldColor: Int, newColor: Int) {
        if (sr !in image.indices ||
            sc !in image[sr].indices ||
            image[sr][sc] != oldColor ||
            image[sr][sc] == newColor
        ) return

        image[sr][sc] = newColor

        floodFill(image, sr + 1, sc, oldColor, newColor)
        floodFill(image, sr - 1, sc, oldColor, newColor)
        floodFill(image, sr, sc - 1, oldColor, newColor)
        floodFill(image, sr, sc + 1, oldColor, newColor)
    }

    fun lastStoneWeight(stones: IntArray): Int {
        val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())

        stones.forEach { maxHeap.add(it) }

        while (maxHeap.size > 1) {
            val newStone = maxHeap.poll() - maxHeap.poll()
            if (newStone > 0) maxHeap.add(newStone)
        }

        return maxHeap.poll() ?: 0
    }

    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || p == null || q == null) return root

        return if (q.`val` < root.`val` && p.`val` < root.`val`) lowestCommonAncestor(root.left, p, q)
        else if (q.`val` > root.`val` && p.`val` > root.`val`) lowestCommonAncestor(root.right, p, q)
        else root
    }

    fun minTimeToVisitAllPoints(points: Array<IntArray>): Int {
        var moves = 0
        var currentX = points[0][0]
        var currentY = points[0][1]

        for (i in 1..points.lastIndex) {
            val targetX = points[i][0]
            val targetY = points[i][1]

            while (currentX != targetX || currentY != targetY) {
                val deltaX = currentX - targetX
                val deltaY = currentY - targetY

                when {
                    deltaX > 0 -> currentX--
                    deltaX < 0 -> currentX++
                }

                when {
                    deltaY > 0 -> currentY--
                    deltaY < 0 -> currentY++
                }

                moves++
            }
        }

        return moves
    }

    fun defangIPaddr(address: String): String = address
        .map { if (it == '.') "[.]" else it }
        .joinToString(separator = "")

    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        val setA = mutableSetOf<ListNode>()

        var currentNode = headA

        while (currentNode != null) {
            setA.add(currentNode)
            currentNode = currentNode.next
        }

        currentNode = headB

        while (currentNode != null) {
            if (setA.contains(currentNode)) return currentNode
            else currentNode = currentNode.next
        }

        return null
    }

    fun missingNumber(nums: IntArray): Int = (0..nums.size).sum() - nums.sum()

    fun singleNumber(nums: IntArray): Int {
        val missingNumber = mutableSetOf<Int>()

        nums.forEach {
            if (missingNumber.contains(it)) missingNumber.remove(it)
            else missingNumber.add(it)
        }

        return missingNumber.first()
    }

    fun longestCommonPrefix(strs: Array<String>): String {
        var commonPrefix = strs[0]

        strs.forEach {
            while (it.startsWith(commonPrefix).not()) {
                commonPrefix = commonPrefix.dropLast(1)
            }
        }

        return commonPrefix
    }

    fun firstUniqChar(s: String): Int {
        val frequency = mutableMapOf<Char, Int>()

        s.forEach { frequency[it] = frequency.getOrDefault(it, 0) + 1 }
        s.forEachIndexed { index, c -> if (frequency[c] == 1) return index }

        return -1
    }

    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null) return l2
        if (l2 == null) return l1

        var currentNode: ListNode?
        var currentL1Node: ListNode? = l1
        var currentL2Node: ListNode? = l2

        if (l1.`val` <= l2.`val`) {
            currentNode = l1
            currentL1Node = l1.next
        } else {
            currentNode = l2
            currentL2Node = l2.next
        }

        val root = currentNode

        while (currentL1Node != null || currentL2Node != null) {
            var minNode: ListNode? = null

            when {
                currentL1Node == null -> {
                    minNode = currentL2Node
                    currentL2Node = currentL2Node?.next
                }
                currentL2Node == null -> {
                    minNode = currentL1Node
                    currentL1Node = currentL1Node.next
                }
                currentL1Node.`val` <= currentL2Node.`val` -> {
                    minNode = currentL1Node
                    currentL1Node = currentL1Node.next
                }
                else -> {
                    minNode = currentL2Node
                    currentL2Node = currentL2Node.next
                }
            }

            currentNode?.next = minNode
            currentNode = currentNode?.next
        }

        return root
    }

    fun mostCommonWord(paragraph: String, banned: Array<String>): String {
        val frequency = mutableMapOf<String, Int>()
        val delimiters = arrayOf(" ", "!", "?", "'", ",", ";", ".")

        paragraph.toLowerCase().split(*delimiters).forEach {
            val key = it
            frequency[key] = frequency.getOrDefault(key, 0) + 1
        }

        val sortedFrequency = frequency.entries.sortedByDescending { it.value }

        sortedFrequency.forEach {
            val word = it.key

            if (banned.contains(word).not() && ("" == word).not()) return word
        }

        error("Word not found")
    }

    fun bubbleSort(array: IntArray) {
        for (index in array.indices) {
            for (index2 in index + 1..array.lastIndex) {
                if (array[index] > array[index2]) {
                    val temp = array[index2]
                    array[index2] = array[index]
                    array[index] = temp
                }
            }
        }
    }

    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        if (root == null) return false
        if (targetSum < 0) return false

        val currentVal = targetSum - root.`val`

        if (currentVal == 0 && root.left == null && root.right == null) return true

        return hasPathSum(root.left, currentVal) || hasPathSum(root.right, currentVal)
    }


    fun isSubtree(root: TreeNode?, subRoot: TreeNode?): Boolean {
        if (root == null) return subRoot == null
        if (subRoot == null) return false

        var subTree = false

        if (root.`val` == subRoot.`val`)
            subTree = isSubtree(root.left, subRoot.left) && isSubtree(root.right, subRoot.right)

        if (subTree.not()) subTree = isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)

        return subTree
    }


    fun postorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        postorderTraversal(root, result)

        return result
    }

    fun postorderTraversal(root: TreeNode?, result: MutableList<Int>) {
        if (root == null) return

        postorderTraversal(root.left, result)
        postorderTraversal(root.right, result)

        result.add(root.`val`)
    }

    fun isPalindrome(s: String): Boolean = s
        .toLowerCase()
        .filter { it.isLetterOrDigit() }
        .let { it == it.reversed() }

    fun validPalindrome(s: String): Boolean {
        var lowerIndex = 0
        var upperIndex = s.lastIndex

        while (lowerIndex < upperIndex) {
            if (s[lowerIndex] != s[upperIndex]) {
                return isPalindrome(s.removeRange(lowerIndex, lowerIndex + 1)) ||
                        isPalindrome(s.removeRange(upperIndex, upperIndex + 1))
            }

            lowerIndex++
            upperIndex--
        }
        return true
    }

    fun isAnagram(s: String, t: String): Boolean {
        val sCharFrequency = mutableMapOf<Char, Int>()
        val tCharFrequency = mutableMapOf<Char, Int>()

        s.forEach { sCharFrequency[it] = sCharFrequency.getOrDefault(it, 0) + 1 }
        t.forEach { tCharFrequency[it] = tCharFrequency.getOrDefault(it, 0) + 1 }

        if (sCharFrequency.size != tCharFrequency.size) return false

        sCharFrequency.forEach { (char, frequency) ->
            if (tCharFrequency[char] != frequency) return false
        }

        return true
    }

    fun isValid(s: String): Boolean {
        val close = setOf(')', '}', ']')
        val stack = Stack<Char>()

        s.forEach {
            if (close.contains(it)) {
                if (stack.isEmpty()) return false

                val previous = stack.pop()

                if (previous == '(' && it != ')') return false
                if (previous == '{' && it != '}') return false
                if (previous == '[' && it != ']') return false

            } else stack.add(it)
        }

        return stack.isEmpty()
    }

    fun backspaceCompare(s: String, t: String): Boolean {
        val sb = StringBuilder()
        val tb = StringBuilder()

        s.forEach {
            if (it != '#') sb.append(it)
            else if (sb.isNotEmpty()) sb.deleteCharAt(sb.lastIndex)
        }

        t.forEach {
            if (it != '#') tb.append(it)
            else if (tb.isNotEmpty()) tb.deleteCharAt(tb.lastIndex)
        }

        return sb.toString() == tb.toString()
    }

    fun sortedArrayToBST(nums: IntArray): TreeNode? = if (nums.isEmpty()) null
    else toBST(nums, 0, nums.lastIndex)

    private fun toBST(nums: IntArray, lowerIndex: Int, upperIndex: Int): TreeNode? {
        if (lowerIndex > upperIndex) return null

        val middle = lowerIndex + (upperIndex - lowerIndex) / 2

        val node = TreeNode(nums[middle])

        node.left = toBST(nums, lowerIndex, middle - 1)
        node.right = toBST(nums, middle + 1, upperIndex)

        return node
    }
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}


class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
