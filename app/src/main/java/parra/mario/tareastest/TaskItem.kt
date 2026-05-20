package parra.mario.tareastest


import android.graphics.pdf.models.ListItem

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material.icons.Icons

@Composable
fun TaskItem(
    task: TaskEntity,
    onToggleCompleted: () -> Unit,
    onDelete: () -> Unit
){
    val dateFormat = remember {
        SimpleDateFormat("dd/MM HH:mm")
    }

    val fechaTexto = remember(task.createdAt){
        dateFormat.format(Date(task.createdAt))
    }

    ListItem(
        leadingContent = {
            Checkbox(
                checked = task.isCompleted,

                onCheckedChange = { onToggleCompleted() }
            )
        },
        headlineContent = {
            Text(
                text = task.title,
                textDecoration = if (task.isCompleted)
                    TextDecoration.LineThrough else null,
                color = if (task.isCompleted)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.onSurface
            )
        },
        supportingContent = {
            Text(text = fechaTexto)
        },
        trailingContent = {
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(
                        R.string.delete_action_desc
                    )
                )
            }
        }
    )
}