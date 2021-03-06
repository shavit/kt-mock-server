/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package kt.mock.server

import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import com.google.gson.JsonObject

class LibraryTest {

    final val MEDIATYPE_JSON = "application/json; charset=utf-8".toMediaType()

    val client = OkHttpClient.Builder().build()
    val server = Library()    

    fun getJson(path: String, obj: JsonObject): Response {
	val url = server.getUrl(path)
	val body = obj.toString().toRequestBody(MEDIATYPE_JSON)
	val req = Request.Builder()
            .header("Content-Type", "application/json; charset=utf-8")
            .url(url)
            .post(body)
            .build()

	return  client.newCall(req).execute()
    }

    @Before fun setup() {
	server.start()
    }
    @After fun shutdown() {
	server.shutdown()
    }

    @Test fun testNotFound() {
	val obj = JsonObject()
        obj.addProperty("email", "some email")
        obj.addProperty("password", "some password")

	val res = getJson("/", obj)	
	assertEquals(404, res.code)	
	val resBody = res.body!!.string()
	assertEquals("not found", resBody)
    }

    @Test fun testAuthNew() {
	val obj = JsonObject()
        obj.addProperty("email", "some email")
        obj.addProperty("password", "some password")

	val res = getJson("/auth/new", obj)	
	assertEquals(200, res.code)	
	val resBody = res.body!!.string()
	// assertEquals("it works", resBody)
    }

    @Test fun testAuth() {
	val obj = JsonObject()
        obj.addProperty("email", "some email")
        obj.addProperty("password", "some password")

	val res = getJson("/auth", obj)	
	assertEquals(200, res.code)	
	val resBody = res.body!!.string()
	// assertEquals("it works", resBody)
    }
}
