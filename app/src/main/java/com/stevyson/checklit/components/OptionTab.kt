package com.stevyson.checklit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stevyson.checklit.ui.theme.Behr
import com.stevyson.checklit.ui.theme.CheckLitTheme
import com.stevyson.checklit.ui.theme.Destructive
import com.stevyson.checklit.ui.theme.PurpleGrey80
import com.stevyson.checklit.ui.theme.Shapes
import com.stevyson.checklit.ui.theme.TextPrimary
import com.stevyson.checklit.ui.theme.Typography

@Composable
fun OptionTab(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String? = null,
    description: String? = null,
    isDestructive: Boolean = false
){
    val textColor = if (isDestructive) Destructive else TextPrimary

    Surface(
        shape = RectangleShape,
        color = Behr,
        modifier = modifier.clip(shape = Shapes.large),
        shadowElevation = 4.dp

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(width = 150.dp, height = 130.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp).size(width = 130.dp, height = 170.dp)
            ) {
                if (icon != null) {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = if (isDestructive) Destructive else PurpleGrey80
                    )
                }

                if (label != null) {
                    Text(
                        text = label,
                        style = Typography.titleMedium,
                        color = textColor,
                        textAlign = TextAlign.Center,
                    )
                }

                if (description != null) {
                    Text(
                        text = description,
                        style = Typography.labelMedium,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                }

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewAdd() {
    CheckLitTheme {
        OptionTab(icon = Icons.Rounded.DateRange, label = "Timezone", description = "select your timezone")
    }
}