package de.cristiano.marathon

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DailyCodingProblemTest {
    private val dcp = DailyCodingProblem()


    @Test
    fun arraysIntersectionTest() {
        // When
        val intersection =
            dcp.arraysIntersection(intArrayOf(1, 2, 3, 4, 5), intArrayOf(1, 2, 5, 7, 9), intArrayOf(1, 3, 4, 5, 8))

        // Then
        assertThat(intersection).contains(0, 1)
        assertThat(intersection).contains(1, 5)
    }

}
