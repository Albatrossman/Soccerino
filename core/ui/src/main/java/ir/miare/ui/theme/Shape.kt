package ir.miare.ui.theme

import android.R.attr.innerRadiusRatio
import androidx.compose.material3.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

internal val shapes = Shapes()

class StarShape(
    private val points: Int = 7,
    private val smoothness: Float = 0.88f,
    private val innerRadiusRatio: Float = 0.72f
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val outerRadius = minOf(centerX, centerY) * 0.9f
        val innerRadius = outerRadius * innerRadiusRatio

        val path = Path()
        val totalPoints = points * 2 // Outer and inner points
        val angleStep = (2 * PI / totalPoints).toFloat()
        val startAngle = -PI.toFloat() / 2 // Start from top

        // Generate all star points with perfect symmetry
        val starPoints = mutableListOf<Pair<Float, Float>>()

        for (i in 0 until totalPoints) {
            val angle = startAngle + i * angleStep
            val radius = if (i % 2 == 0) outerRadius else innerRadius

            val x = centerX + cos(angle) * radius
            val y = centerY + sin(angle) * radius
            starPoints.add(x to y)
        }

        // Start the path at the first point
        path.moveTo(starPoints[0].first, starPoints[0].second)

        // Create smooth curves through all points using cubic Bézier curves
        for (i in starPoints.indices) {
            val currentIndex = i
            val nextIndex = (i + 1) % starPoints.size

            val currentPoint = starPoints[currentIndex]
            val nextPoint = starPoints[nextIndex]

            // Calculate control points for symmetrical smooth curves
            val smoothingFactor = smoothness * 0.3f

            // Get the angle from center to current and next points
            val currentAngle = startAngle + currentIndex * angleStep
            val nextAngle = startAngle + nextIndex * angleStep

            // Calculate control points based on the tangent directions
            // For perfect symmetry, use perpendicular directions to the radial lines
            val currentTangentAngle = currentAngle + PI.toFloat() / 2
            val nextTangentAngle = nextAngle - PI.toFloat() / 2

            // Distance between current and next point
            val dx = nextPoint.first - currentPoint.first
            val dy = nextPoint.second - currentPoint.second
            val distance = sqrt(dx * dx + dy * dy)
            val controlDistance = distance * smoothingFactor

            // Control point 1: from current point along its tangent
            val control1X = currentPoint.first + cos(currentTangentAngle) * controlDistance
            val control1Y = currentPoint.second + sin(currentTangentAngle) * controlDistance

            // Control point 2: approaching next point along its tangent
            val control2X = nextPoint.first + cos(nextTangentAngle) * controlDistance
            val control2Y = nextPoint.second + sin(nextTangentAngle) * controlDistance

            // Draw smooth cubic curve to next point
            path.cubicTo(
                control1X, control1Y,
                control2X, control2Y,
                nextPoint.first, nextPoint.second
            )
        }

        path.close()
        return Outline.Generic(path)
    }

}