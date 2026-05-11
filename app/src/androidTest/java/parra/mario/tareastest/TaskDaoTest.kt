package parra.mario.tareastest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: TaskDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.taskDao()
    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun insertarTarea() =
        runTest {
            var titulo = "tarea de base de datos"

            val tarea = TaskEntity(
                title = titulo
            )

            dao.insert(tarea)

            val tareas = dao.getAllTasks().first()

            assertEquals(1, tareas.size)
            assertEquals(
                titulo, tareas[0].title
            )
        }

    @Test
    fun actualizarTarea() =
        runTest{
            dao.insert(
                TaskEntity(title = "Estudiar")
            )
            val original = dao.getAllTasks().first().first()

            assertEquals(false, original.isCompleted)

            dao.update(original.copy(
                isCompleted = true
            ))

            val actualizada = dao.getAllTasks().first().first()

            assertEquals(true, actualizada.isCompleted)
        }


    @Test
    fun borrarTarea() =
        runTest {

            dao.insert(
                TaskEntity(title = "Algo")
            )

            var tareas = dao.getAllTasks().first()
            var tarea = tareas.first()

            assertEquals(1, tareas.size)

            dao.delete(tarea)
            tareas = dao.getAllTasks().first()
            assertEquals(0, tareas.size)



        }


}